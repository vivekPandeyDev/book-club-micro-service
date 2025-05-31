package org.loop.troop.domain.permitio;

import io.permit.sdk.Permit;
import io.permit.sdk.api.PermitApiError;
import io.permit.sdk.enforcement.Resource;
import io.permit.sdk.enforcement.User;
import io.permit.sdk.util.Context;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermitAuthorizationService {

    private final Permit permit;

    public boolean isAllowed(PermitUserObject userObject,
                             String action,
                             PermitResourceObject resourceObject,
                             Context context) {
        try {
            User user = userObject.toPermitUser();
            Resource resource = resourceObject.toPermitResource();

            return permit.check(user, action, resource, context);

        } catch (IOException | PermitApiError e) {
            log.error("Error : {}",e.getMessage());
            return false;
        }
    }
}