package com.recruitment.user;

import java.time.ZonedDateTime;

public record User(
        long id,
        String login,
        String name,
        String type,
        String avatarUrl,
        ZonedDateTime createdAt,
        float calculations) {}
