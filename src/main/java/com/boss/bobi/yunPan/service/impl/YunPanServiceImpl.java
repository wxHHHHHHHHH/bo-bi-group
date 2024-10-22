package com.boss.bobi.yunPan.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.boss.bobi.common.enums.ResultCode;
import com.boss.bobi.common.utils.RestClientUtil;
import com.boss.bobi.config.TokenValueConfig;
import com.boss.bobi.login.entity.User;
import com.boss.bobi.login.service.UserService;
import com.boss.bobi.yunPan.entity.CloudStorageInfo;
import com.boss.bobi.yunPan.entity.FileInfo;
import com.boss.bobi.yunPan.model.*;
import com.boss.bobi.yunPan.service.CloudStorageInfoService;
import com.boss.bobi.yunPan.service.FileInfoService;
import com.boss.bobi.yunPan.service.YunPanService;
import com.boss.bobi.common.model.CommonResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @ClassName : YunPanServiceIml  //类名
 * @Description :   //描述
 * @Date: 2024-08-05  18:04
 */
@Service
@Slf4j
public class YunPanServiceImpl  implements YunPanService {

    @Autowired
    private CloudStorageInfoService cloudStorageInfoService;

    @Autowired
    private UserService userService;

    @Value("${yunPan.pre}")
    private String preUrl;

    @Value("${yunPan.mkdir}")
    private String mkdir;

    @Value("${yunPan.create}")
    private String create;

    @Value("${yunPan.fileList}")
    private String fileList;

    @Value("${yunPan.direct-linkUrl}")
    private String directLinkUrl;

    @Value("${yunPan.getM3u8}")
    private String getM3u8;

    @Value("${yunPan.direct-linkEnable}")
    private String directLinkEnable;

    @Value("${yunPan.access_token_url}")
    private String accessTokenUrl;
    @Value("${yunPan.get_upload_url}")
    private String getUploadUrl;

    @Value("${yunPan.request_header_platform}")
    private String requestHeaderPlatform;

    @Value("${yunPan.request_header_platform_value}")
    private String requestHeaderPlatformValue;

    @Value("${yunPan.request_header_authorization}")
    private String requestHeaderAuthorization;

    @Value("${yunPan.upload_complete}")
    private String uploadComplete;

    @Value("${yunPan.upload_async_result}")
    private String uploadAsyncResult;

    @Value("${yunPan.parentFileID}")
    private Integer parentFileID;

    @Autowired
    private RestClientUtil restClientUtil;

    @Autowired
    private TokenValueConfig tokenValueConfig;

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private OkHttpClient client;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public String uploadImg(MultipartFile file) throws IOException {


            String md5 = DigestUtils.md5Hex(file.getBytes());
            FileInfo one = fileInfoService.getOne(new LambdaQueryWrapper<FileInfo>().eq(FileInfo::getFileMd5, md5));
            if (one!=null) {
                return one.getFileDirectLinkUrl();
            }
            Map<String, String> map = new HashMap<>();
            map.put(requestHeaderPlatform, requestHeaderPlatformValue);
            CloudStorageInfo accessToken = getAccessToken(map);
            map.put(requestHeaderAuthorization, accessToken.getAccessToken());
            Integer dirId = tokenValueConfig.getDirID();
            Integer id = tokenValueConfig.getId();
            if (dirId == null) {
                dirId = createDirId(tokenValueConfig.getNickName().concat("-" + id), map);
            }
            Long size = file.getSize();
            String originalFilename = file.getOriginalFilename();
            int lastIndex = originalFilename.lastIndexOf('.');
            String fileExtension = originalFilename.substring(lastIndex);
            YCreateFile yCreateFile = createFile(dirId, md5.concat(fileExtension), md5, size, map);
            //判断是否为秒传
            if (!yCreateFile.getReuse()) {
//              //
                byte[] fileBytes = file.getBytes();
                String preuploadID = yCreateFile.getPreuploadID();
                Long sliceSize = yCreateFile.getSliceSize();
                int totalSlices = (int) Math.ceil((double) size / sliceSize);
                for (int sliceNo = 1; sliceNo <= totalSlices; sliceNo++) {
                    String uploadUrl = getUploadUrl(preuploadID, sliceNo, map);
                    long start = (sliceNo - 1) * sliceSize;
                    long end = Math.min(sliceNo * sliceSize, size);
                    byte[] buffer = new byte[(int) (end - start)];
                    // 复制文件字节到缓冲区
                    System.arraycopy(fileBytes, (int) start, buffer, 0, buffer.length);
                    RequestBody body = RequestBody.create(buffer, MediaType.get("application/octet-stream"));
                    Request request = new Request.Builder()
                            .url(uploadUrl)
                            .put(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }
                }
                YUploadComplete yUploadComplete = uploadComplete(preuploadID, map);
                if (yUploadComplete.getAsync()) {
                    YUploadAsyncResult yUploadAsyncResult = uploadAsyncResult(preuploadID, map);
                    if (yUploadAsyncResult != null) {
                        yCreateFile.setFileID(yUploadAsyncResult.getFileID());
                    }
                } else {
                    yCreateFile.setFileID(yUploadComplete.getFileID());

                }

            }

            Integer fileID = yCreateFile.getFileID();
            String directLinkUrl = getDirectLinkUrl(fileID,map);

            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileMd5(md5);
            fileInfo.setFileDirectLinkUrl(directLinkUrl);
            fileInfo.setNickName(tokenValueConfig.getNickName());
            fileInfo.setUserId(tokenValueConfig.getId());
            fileInfo.setInsertTime(new Date());
            fileInfo.setOriginName(file.getOriginalFilename());
            fileInfo.setFileId(fileID);

            fileInfoService.save(fileInfo);
            return directLinkUrl;
    }
    private  void uploadFile(String uploadUrl, byte[] data) throws IOException {
        String body = restClientUtil.putUpload(uploadUrl, data);
        log.info("调用上传分片接口返回数据：{}", body);

//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) {
//                throw new IOException("Unexpected code " + response);
//            }
//
//            JsonNode result = mapper.readTree(response.body().string());
//            if (result.get("code").asInt() != 0) {
//                throw new IOException(result.get("message").asText());
//            }
//        }
    }





    private String getServeUploadUrl(String preuploadID,int chunkNumber, Map<String,String> header){
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("preuploadID", preuploadID);
        requestParam.put("sliceNo", chunkNumber);

        CommonResult<?> commonResult = restClientUtil.postJson(preUrl.concat(getUploadUrl), header, requestParam, CommonResult.class);
        log.info("请求云盘身份验证接口响应结果：{}", commonResult);
        if (Objects.equals(commonResult.getCode(), ResultCode.YSUCCEED.getResult())) {
            JSONObject data = JSON.parseObject(JSON.toJSONString(commonResult.getData()));
            String presignedURL = data.getString("presignedURL");
            return presignedURL;
        }
        return null;

    }
    @Override
    public CloudStorageInfo getAccessToken(Map<String,String> map) {
        CloudStorageInfo cloudStorageInfo = cloudStorageInfoService.getById(1);
        String expiredAt = cloudStorageInfo.getExpiredAt();
        String accessToken = cloudStorageInfo.getAccessToken();
        if (StringUtils.isNotBlank(accessToken) && StringUtils.isNotBlank(expiredAt)) {
            // 解析字符串为 ZonedDateTime 对象
            ZonedDateTime parsedDateTime = ZonedDateTime.parse(expiredAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

            // 转换为时间戳（Unix 时间戳，毫秒）
            long timestamp = parsedDateTime.toInstant().toEpochMilli();
            // 获取当前时间的时间戳（毫秒）
            long currentTimestamp = Instant.now().toEpochMilli();
            if (timestamp > currentTimestamp) {

                return cloudStorageInfo;
            }
        }
        CommonResult<?> commonResult = restClientUtil.postJson(preUrl.concat(accessTokenUrl), map, cloudStorageInfo, CommonResult.class);
        log.info("请求云盘身份验证接口响应结果：{}", JSON.toJSONString(commonResult));
        if (Objects.equals(commonResult.getCode(), ResultCode.YSUCCEED.getResult())) {
            YAccessToken yAccessToken = JSONObject.parseObject(JSON.toJSONString(commonResult.getData()), YAccessToken.class);
            cloudStorageInfo.setExpiredAt(yAccessToken.getExpiredAt());
            cloudStorageInfo.setAccessToken(yAccessToken.getAccessToken());
            cloudStorageInfoService.updateById(cloudStorageInfo);
            return cloudStorageInfo;
        }
        return null;
    }

    @Override
    public Integer createDirId(String name,Map<String,String> map) {
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("name", name);
        requestParam.put("parentID", parentFileID);
        CommonResult<?> commonResult = restClientUtil.postJson(preUrl.concat(mkdir), map, requestParam, CommonResult.class);
        log.info("请求云盘创建目录接口响应结果：{}", JSON.toJSONString(commonResult));
        if (Objects.equals(commonResult.getCode(), ResultCode.YSUCCEED.getResult())) {
            JSONObject data = JSONObject.parseObject(JSON.toJSONString(commonResult.getData()));
            Integer dirID = data.getInteger("dirID");
            userService.update(new LambdaUpdateWrapper<User>().eq(User::getNickName, name).set(User::getDirId, dirID));
            return dirID;
        }
        return null;
    }

    @Override
    public YCreateFile createFile(Integer parentFileID, String filename, String etag, Long size, Map<String, String> map) {
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("parentFileID", parentFileID);
        requestParam.put("filename", filename);
        requestParam.put("etag", etag);
        requestParam.put("size", size);
        CommonResult<?> commonResult = restClientUtil.postJson(preUrl.concat(create), map, requestParam, CommonResult.class);
        log.info("请求云盘创建文件接口响应结果：{}", JSON.toJSONString(commonResult));
        if (Objects.equals(commonResult.getCode(), ResultCode.YSUCCEED.getResult())) {
            YCreateFile yCreateFile = JSONObject.parseObject(JSON.toJSONString(commonResult.getData()),YCreateFile.class);
            return yCreateFile;
        }
        return null;
    }

    @Override
    public String getDirectLinkUrl(Integer fileID, Map<String, String> map) {
        CommonResult<?> commonResult = restClientUtil.get(preUrl.concat(directLinkUrl).concat("?fileID=" + fileID), map, null, CommonResult.class);
        log.info("请求云盘获取直链接口响应结果：{}", JSON.toJSONString(commonResult));
        if (Objects.equals(commonResult.getCode(), ResultCode.YSUCCEED.getResult())) {
            JSONObject data = JSONObject.parseObject(JSON.toJSONString(commonResult.getData()));
            return data.getString("url");
        }
        return null;
    }

    @Override
    public List<Parts> listUploadParts(Integer parentFileID, String filename, String etag, Long size, Map<String, String> map) {
        return null;
    }

    @Override
    public String getUploadUrl(String preuploadID, Integer sliceNo,Map<String, String> map) {
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("preuploadID", preuploadID);
        requestParam.put("sliceNo", sliceNo);
        CommonResult<?> commonResult = restClientUtil.postJson(preUrl.concat(getUploadUrl), map, requestParam, CommonResult.class);
        log.info("请求云盘获取上传地址响应结果：{}", JSON.toJSONString(commonResult));
        if (Objects.equals(commonResult.getCode(), ResultCode.YSUCCEED.getResult())) {
            JSONObject data = JSONObject.parseObject(JSON.toJSONString(commonResult.getData()));
            return data.getString("presignedURL");
        }
        return null;
    }

    @Override
    public YUploadComplete uploadComplete(String preuploadID,Map<String,String> map ) {

        Map<String, String> request = new HashMap<>();
        request.put("preuploadID", preuploadID);
        CommonResult<?> commonResult = restClientUtil.postJson(preUrl.concat(uploadComplete), map, request, CommonResult.class);
        log.info("调用上传完毕响应结果：{}", JSON.toJSONString(commonResult));
        if (Objects.equals(commonResult.getCode(), ResultCode.YSUCCEED.getResult())) {
            YUploadComplete data = JSONObject.parseObject(JSON.toJSONString(commonResult.getData()),YUploadComplete.class);
            return data;
        }
        return null;
    }

    @Override
    public YUploadAsyncResult uploadAsyncResult(String preuploadID, Map<String, String> map) {
        while (true) {
            Map<String, String> request = new HashMap<>();
            request.put("preuploadID", preuploadID);
            CommonResult<?> commonResult = restClientUtil.postJson(preUrl.concat(uploadAsyncResult), map, request, CommonResult.class);
            log.info("调用循环结果：{}", JSON.toJSONString(commonResult));
            if (Objects.equals(commonResult.getCode(), ResultCode.YSUCCEED.getResult())) {
                YUploadAsyncResult data = JSONObject.parseObject(JSON.toJSONString(commonResult.getData()), YUploadAsyncResult.class);
                if (data.getCompleted()) {
                    return data;
                } else {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                return null;
            }
        }
    }
}
