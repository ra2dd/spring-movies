package com.example.movies.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class AppConfig {

    @Bean
    public HttpClient httpClient() {
        return HttpClient
                .newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }
}
