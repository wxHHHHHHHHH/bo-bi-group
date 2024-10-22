package com.boss.bobi.yunPan.service;

import com.boss.bobi.yunPan.entity.CloudStorageInfo;
import com.boss.bobi.yunPan.model.*;
import com.boss.bobi.common.model.CommonResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface YunPanService {

    String uploadImg(MultipartFile file) throws IOException;

    /**
     *  获取token
     */
    CloudStorageInfo getAccessToken(Map<String,String> map);



    /**
     * 创建目录
     */
    Integer createDirId(String name,Map<String,String> map);


    /**
     *  创建文件
     */
    YCreateFile createFile(Integer parentFileID, String filename, String etag, Long size, Map<String, String> map);


    /**
     * 列举已上传分片
     */
    List<Parts> listUploadParts(Integer parentFileID, String filename, String etag, Long size, Map<String, String> map);


    /**
     * 获取直连地址
     */
    String getDirectLinkUrl(Integer fileID, Map<String, String> map);


    /**
     * 获取上传地址
     */
    String getUploadUrl(String preuploadID,Integer sliceNo,Map<String, String> map);


    /**
     * 上传完毕
     */
    YUploadComplete uploadComplete(String preuploadID,Map<String,String> map);

    /**
     * 异步轮询获取上传结果
     */

    YUploadAsyncResult uploadAsyncResult(String preuploadID,Map<String, String> map);

}
