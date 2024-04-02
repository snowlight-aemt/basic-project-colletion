package me.snowlight.springsecurityjwt.config.security.principal;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtUserAuthenticationToken extends AbstractAuthenticationToken {
    private final UserPrincipal principal;

    public JwtUserAuthenticationToken(UserPrincipal principal) {
        super(principal.getAuthorities());
        super.setAuthenticated(true);
        this.principal = principal;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
