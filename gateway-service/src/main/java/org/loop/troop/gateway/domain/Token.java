package org.loop.troop.gateway.domain;

import lombok.Data;

@Data
public class Token {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private String scope;
    private String sessionState;
    private Long expiresIn;
    private Long refreshExpiresIn;

}
