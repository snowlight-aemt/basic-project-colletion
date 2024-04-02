package me.snowlight.springsecurityjwt.config.security.principal;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtPreAuthenticationToken extends AbstractAuthenticationToken {
    private final String token;

    public JwtPreAuthenticationToken(String token) {
        super(null);
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
