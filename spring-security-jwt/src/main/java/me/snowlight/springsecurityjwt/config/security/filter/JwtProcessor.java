package me.snowlight.springsecurityjwt.config.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import me.snowlight.springsecurityjwt.config.security.principal.UserPrincipal;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class JwtProcessor {
    public String generateToken(UserPrincipal principal) {
        long nowMillis = Clock.systemDefaultZone().millis();
        Date issuedDt = new Date(nowMillis);
        Date expiration = new Date(nowMillis + "111111111");

        return Jwts.builder().claims(principal.claims()).issuedAt(issuedDt).expiration(expiration).compact();
    }

    public UserPrincipal getPrincipal(String token) {
        // assert
        return generatePrincipal(token);
    }

    private UserPrincipal generatePrincipal(String token) {
        Claims claims = getClaims(token);

        UUID id = UUID.fromString(claims.get("id", String.class));
        String name = claims.get("name", String.class);
        Collection<GrantedAuthority> authorities = claims.get("authorities", Collection.class).stream()
                .map(n -> new SimpleGrantedAuthority(n.toString())).toList();

        return new UserPrincipal(id, name, authorities);
    }

    private static Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode("1111"))).build()
                    .parseSignedClaims(token).getPayload();
        } catch (JwtException e) {
            throw new BadCredentialsException(token + " does not exists");
        }
    }
}
