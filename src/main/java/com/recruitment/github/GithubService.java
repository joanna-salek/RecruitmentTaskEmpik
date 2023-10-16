package com.recruitment.github;

import org.springframework.web.reactive.function.client.WebClient;

public class GithubService {
    private final WebClient webClient;

    public GithubService(WebClient webClient) {
        this.webClient = webClient;
    }

    public GithubUser getUser(String login) {
        return webClient
                .get()
                .uri("/users/{login}", login)
                .retrieve()
                .bodyToMono(GithubUser.class)
                .block();
    }
}
