package org.loop.troop.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
    @GetMapping
    public String guest() {
        return "Hello from Spring boot & Keycloak - Guest";
    }
}
