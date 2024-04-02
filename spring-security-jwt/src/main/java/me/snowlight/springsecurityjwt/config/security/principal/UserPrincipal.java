package me.snowlight.springsecurityjwt.config.security.principal;

import io.jsonwebtoken.lang.Maps;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class UserPrincipal {
    private final UUID id;
    private final String name;
    private final Collection<GrantedAuthority> authorities;

    public UserPrincipal(User user) {
        this.id = user.getId();
        this.name = user.getName();
        authorities = List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    public UserPrincipal(UUID id, String name, Collection<GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.authorities = authorities;
    }

    public Map<String, Object> claims() {
        return Map.of("id", id, "name", name, "authorities", authorities);
    }
}
