package com.isamarques.autominer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Logger;

@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        // FULL registra: URL, Headers, Body da Requisição e da Resposta
        return Logger.Level.FULL;
    }
}