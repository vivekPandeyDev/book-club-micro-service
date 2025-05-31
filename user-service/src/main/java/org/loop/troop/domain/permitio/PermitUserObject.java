package org.loop.troop.domain.permitio;

import io.permit.sdk.enforcement.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@Builder
public class PermitUserObject {

    private final String key;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final HashMap<String, Object> attributes;

    public User toPermitUser() {
        return new User.Builder(key)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withAttributes(attributes)
                .build();
    }
}
