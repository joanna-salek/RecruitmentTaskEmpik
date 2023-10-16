package com.recruitment.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;

public record GithubUser(
        long id,
        String login,
        String name,
        String type,
        @JsonProperty("avatar_url") String avatarUrl,
        @JsonProperty("created_at") ZonedDateTime createdAt,
        @JsonProperty("public_repos") int publicRepos,
        long followers) {}
