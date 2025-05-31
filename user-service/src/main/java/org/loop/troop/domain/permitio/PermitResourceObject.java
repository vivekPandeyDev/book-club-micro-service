package org.loop.troop.domain.permitio;

import io.permit.sdk.enforcement.Resource;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@Builder
public class PermitResourceObject {

    private final String type;
    private final String key;
    private final String tenant;
    private final HashMap<String, Object> attributes = new HashMap<>();
    private final HashMap<String, Object> context = new HashMap<>();

    public Resource toPermitResource() {
        return new Resource.Builder(type + ":" + key)
                .withTenant(tenant)
                .withAttributes(attributes)
                .withContext(context)
                .build();
    }
}