package com.recruitment.github;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GithubConfiguration {
    @Bean
    public GithubService githubService(WebClient.Builder webClientBuilder) {
        final String URL = "https://api.github.com";
        return new GithubService(webClientBuilder.baseUrl(URL).build());
    }
}
