package com.boss.bobi;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


/**
 * @ClassName : BossBoBiApplication  //类名
 * @Description : 入口  //描述
 * @Date: 2024-07-02  15:50
 */

@SpringBootApplication(exclude = SecurityAutoConfiguration.class, scanBasePackages = {"com.boss.bobi"})
@MapperScan(basePackages = {"com.boss.bobi.**.mapper"})
public class BossBoBiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BossBoBiApplication.class, args);
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

}
