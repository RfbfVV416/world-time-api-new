package org.world_time_api_new.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${worldtime: Default url}")
    private String externalApiUrl;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEnumConverter());
    }

    @Bean
    public WebClient buildWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(externalApiUrl)
                .build();
    }
}

