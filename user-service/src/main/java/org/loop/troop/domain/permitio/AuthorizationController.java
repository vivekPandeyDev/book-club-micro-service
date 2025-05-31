package org.loop.troop.domain.permitio;

import io.permit.sdk.Permit;
import io.permit.sdk.enforcement.Resource;
import io.permit.sdk.enforcement.User;
import io.permit.sdk.api.PermitApiError;
import io.permit.sdk.util.Context;
import org.loop.troop.handler.PermitCheck;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/authorize")
public class AuthorizationController {

    private final Permit permit;

    public AuthorizationController(Permit permit) {
        this.permit = permit;
    }

    @GetMapping
    @PermitCheck(action = "read",resource = "document")
    public ResponseEntity<Boolean> documentCreate(){
        return ResponseEntity.ok(true);
    }

    @PostMapping
    public ResponseEntity<Boolean> authorize(@RequestBody AuthorizationRequestDTO request) {
        try {
            // Build User
            User user = new User.Builder(request.getUserKey())
                    .withFirstName(request.getFirstName())
                    .withLastName(request.getLastName())
                    .withEmail(request.getEmail())
                    .withAttributes(request.getUserAttributes())
                    .build();

            // Build Resource
            Resource resource = new Resource.Builder(request.getResourceType() + ":" + request.getResourceKey())
                    .withTenant(request.getTenant())
                    .withAttributes(request.getResourceAttributes())
                    .withContext(request.getResourceContext())
                    .build();

            // Context
            Context context = new Context();
            if (request.getContext() != null) {
                context.putAll(request.getContext());
            }

            boolean result = permit.check(user, request.getAction(), resource, context);

            return ResponseEntity.ok(result);

        } catch (IOException | PermitApiError e) {
            return ResponseEntity.status(500).body(false);
        }
    }
}
