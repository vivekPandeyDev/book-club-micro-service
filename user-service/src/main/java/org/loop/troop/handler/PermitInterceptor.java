package org.loop.troop.handler;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.permit.sdk.Permit;
import io.permit.sdk.enforcement.Resource;
import io.permit.sdk.enforcement.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PermitInterceptor implements HandlerInterceptor {

    private final Permit permit;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            Method method = handlerMethod.getMethod();
            PermitCheck permitCheck = method.getAnnotation(PermitCheck.class);
            if (permitCheck != null) {
                String authHeader = request.getHeader("Authorization");
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Missing or invalid Authorization header");
                    return false;
                }
                String token = authHeader.substring(7);
                // get annotation value
                String resourceKey = permitCheck.resource();
                String actionName = permitCheck.action();
                final var resource = new Resource.Builder(resourceKey).build();
                // Perform the permission check
                boolean isAllowed = permit.check(getUserFromToken(token), actionName, resource);

                if (!isAllowed) {
                    writeForbiddenResponse(request,response);
                    return false;
                }
            }
        }
        return true;
    }

    private void writeForbiddenResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/problem+json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> problemDetails = new HashMap<>();
        problemDetails.put("type", "https://tools.ietf.org/html/rfc7231#section-6.5.3");
        problemDetails.put("title", "Forbidden");
        problemDetails.put("status", 403);
        problemDetails.put("detail", "You do not have permission to access this resource.");
        problemDetails.put("instance", request.getRequestURI());
        problemDetails.put("timestamp", Instant.now().toString());

        response.getWriter().write(objectMapper.writeValueAsString(problemDetails));
    }
    private User getUserFromToken(String token){
        var jwt = JWT.decode(token);
        String userKey = jwt.getSubject();
        final var username = jwt.getClaim("preferred_username").asString();
        final var email = jwt.getClaim("email").asString();
        final var roles = jwt.getClaim("roles").asList(String.class);
        var attributes = new HashMap<String, Object>();
        if (roles != null) {
            attributes.put("roles", roles);
        }
        return new User.Builder(userKey)
                .withFirstName(username)
                .withEmail(email)
                .withAttributes(attributes)
                .build();
    }
}