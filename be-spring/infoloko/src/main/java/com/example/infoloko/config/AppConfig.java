package com.example.infoloko.config;

import com.example.infoloko.model.Summary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean(name= "restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Summary summary(){
        return new Summary();
    }


}
