package com.boss.bobi.config.interceptor;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.boss.bobi.common.enums.ResultCode;
import com.boss.bobi.common.model.CommonResult;
import com.boss.bobi.common.model.RedisNameEnums;
import com.boss.bobi.common.utils.AccessTokenUtil;
import com.boss.bobi.common.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @ClassName : AccessTokenInterceptor  //类名
 * @Description : 验证token拦截器  //描述
 * @Date: 2024-07-03  17:27
 */
@Component
public class AccessTokenInterceptor implements HandlerInterceptor {




    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 执行方法之前做逻辑判断
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头中获取token
        String token = request.getHeader("token");

        if (StringUtils.isNotBlank(token)) {
            //验证token
            boolean verify = JWTUtil.verify(token, JwtUtil.key.getBytes());
            if (verify) {
                JWT jwt = JWTUtil.parseToken(token);
                String nickName = (String) jwt.getPayload("nickName");
                String token2 = redisTemplate.opsForValue().get(RedisNameEnums.TokenKey.TOKEN.getCode() + nickName);
                if (!StringUtils.equals(token, token2)) {
                    //token失效
                    setResponse(response,CommonResult.build(ResultCode.DEFEATED,"token失效！"));
                    return false;
                }
                AccessTokenUtil.setToken(token);

                return true;
            } else {
//              未验证通过
                setResponse(response,CommonResult.build(ResultCode.DEFEATED,"token验证未通过！"));
                return false;
            }

        } else {
            //未登录
            setResponse(response,CommonResult.build(ResultCode.DEFEATED,"请先登录！"));
            return false;
        }


    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AccessTokenUtil.removeToken();
    }

    /**
     * 设置返回值
     * @param response
     * @param result
     */
    private void setResponse(HttpServletResponse response, CommonResult result){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("utf8");
        response.setContentType("application/json");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.println(JSON.toJSONString(result));
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(writer != null){
                writer.close();
            }
        }
    }
}

