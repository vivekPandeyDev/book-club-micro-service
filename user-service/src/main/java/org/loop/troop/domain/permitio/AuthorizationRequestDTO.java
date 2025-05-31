package org.loop.troop.domain.permitio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Getter
@Setter
@ToString
public class AuthorizationRequestDTO {
    private String userKey;
    private String firstName;
    private String lastName;
    private String email;
    private HashMap<String, Object> userAttributes;

    private String action;

    private String resourceType;
    private String resourceKey;
    private String tenant;
    private HashMap<String, Object> resourceAttributes;
    private HashMap<String, Object> resourceContext;

    private HashMap<String, Object> context;
}
