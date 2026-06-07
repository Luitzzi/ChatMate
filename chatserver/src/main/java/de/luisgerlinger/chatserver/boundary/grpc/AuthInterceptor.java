package de.luisgerlinger.chatserver.boundary.grpc;

import de.luisgerlinger.chatserver.security.JwtUtil;
import io.grpc.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.GlobalServerInterceptor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@GlobalServerInterceptor
@RequiredArgsConstructor
public class AuthInterceptor implements ServerInterceptor {

    private static final Metadata.Key<String> AUTHORIZATION_KEY =
            Metadata.Key.of(
                    "authorization",
                    Metadata.ASCII_STRING_MARSHALLER
            );

    private final JwtUtil jwtUtil;

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next
    ) {
        log.info("Interceptor: Extracting the token");
        String authHeader = headers.get(AUTHORIZATION_KEY);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.error("Closing connection. Missing token.");
            call.close(Status.UNAUTHENTICATED
                    .withDescription("Missing token"),
                    new Metadata());
            return new ServerCall.Listener<>() {};
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            log.error("Closing connection. Invalid token.");
            call.close(Status.UNAUTHENTICATED
                            .withDescription("Invalid token"),
                    new Metadata());
            return new ServerCall.Listener<>() {};
        }
        UUID clientId = jwtUtil.extractUserId(token);
        log.info("Extracted clientId: {}", clientId);
        Context context = Context.current()
                .withValue(GrpcSecurityContext.CLIENT_ID, clientId);
        return Contexts.interceptCall(context, call, headers, next);
    }
}
