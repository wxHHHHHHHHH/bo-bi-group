/**
 * 
 */
package com.boss.bobi.config.exception;

import com.boss.bobi.config.interceptor.AccessTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
* <p>Title: WebMvcConfig</p>
* <p>Description: </p>
* <p>Company: ubiai</p>
* @author lanaya
* @date 2019-07-30 14:27
*/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
    
    private static List<String> excludePaths = Arrays.asList("/login/goLogin");

    @Autowired
    private AccessTokenInterceptor accessTokenInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //其他拦截，需携带参数saas_id
        registry.addInterceptor(accessTokenInterceptor).
                excludePathPatterns(excludePaths).addPathPatterns("/**");
    }
    
}
