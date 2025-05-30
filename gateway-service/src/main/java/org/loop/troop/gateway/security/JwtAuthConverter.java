package org.loop.troop.gateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtAuthConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    @Value("${jwt.auth.converter.principle-attribute}")
    private String principleAttribute;

    @Value("${jwt.auth.converter.resource-id}")
    private String resourceId;


    private final List<String> filterRole = List.of("default-roles-auth-server","offline_access","uma_authorization");

    @Override
    public Mono<AbstractAuthenticationToken> convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractResourceRoles(jwt, resourceId);

        return Mono.just(new JwtAuthenticationToken(
                jwt,
                authorities,
                getPrincipleClaimName(jwt)
        ));
    }

    private String getPrincipleClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if (principleAttribute != null) {
            claimName = principleAttribute;
        }
        return jwt.getClaim(claimName);
    }

    private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt, String resourceId) {
        Map<String,Collection<String>> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess == null) {
            return Collections.emptySet();
        }
        Collection<String> resourceRoles = realmAccess.get("roles");

        if (resourceRoles == null) {
            return Collections.emptySet();
        }

        return resourceRoles
                .stream()
                .filter(role -> !filterRole.contains(role.toLowerCase()))
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.toUpperCase(Locale.ENGLISH)))
                .collect(Collectors.toSet());
    }

}
