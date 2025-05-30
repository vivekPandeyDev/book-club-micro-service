package org.loop.troop.gateway.controller;

import org.loop.troop.gateway.domain.LoginRequest;
import org.loop.troop.gateway.domain.Token;
import org.loop.troop.gateway.security.JwtAuthConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    private final JwtAuthConverter jwtAuthConverter;

    @PostMapping("/token")
    public Token getToken(@Valid @RequestBody LoginRequest loginRequest) {
        return tokenService.getToken(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @GetMapping("/token/info")
    public Mono<ResponseEntity<Map<String, String>>> getTokenInfo(@AuthenticationPrincipal Jwt jwt) {
        var authenticationTokenMono = jwtAuthConverter.convert(jwt);
        assert authenticationTokenMono != null;
        return authenticationTokenMono
                .switchIfEmpty(Mono.error(new ServiceException("Invalid token, cannot get the user name")))
                .flatMap(z ->
                        Mono.just(
                                ResponseEntity.ok(
                                        Map.of("user_name", z.getName()
                                        )
                                )
                        )
                );
    }

    @GetMapping
    public String guest() {
        return "Hello from Spring boot & Keycloak - Guest";
    }

    @GetMapping("/read")
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public Mono<String> hello() {

        return Mono.just("Hello from Spring boot & Keycloak - User");
    }

    @GetMapping("/write")
    @PreAuthorize("hasRole('WRITE')")
    public Mono<String> hello2() {
        return Mono.just("Hello from Spring boot & Keycloak - ADMIN");
    }
}
