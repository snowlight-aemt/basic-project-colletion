package me.snowlight.springsecurityjwt.config.security.principal;

import lombok.Getter;

import java.util.UUID;

@Getter
public class User {
    private UUID id;
    private String name;
    private String role;
}
