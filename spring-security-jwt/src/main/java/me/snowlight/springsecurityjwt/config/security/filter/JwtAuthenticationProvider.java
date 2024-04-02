package me.snowlight.springsecurityjwt.config.security.filter;

import lombok.RequiredArgsConstructor;
import me.snowlight.springsecurityjwt.config.security.principal.JwtPreAuthenticationToken;
import me.snowlight.springsecurityjwt.config.security.principal.JwtUserAuthenticationToken;
import me.snowlight.springsecurityjwt.config.security.principal.UserPrincipal;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtProcessor jwtProcessor;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) return null;

        UserPrincipal principal = jwtProcessor.getPrincipal(authentication.getPrincipal().toString());
        return new JwtUserAuthenticationToken(principal);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtPreAuthenticationToken.class);
    }
}
