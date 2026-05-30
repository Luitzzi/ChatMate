package de.luisgerlinger.chatserver.boundary.grpc;

import io.grpc.Context;

import java.util.UUID;

public final class GrpcSecurityContext {
    public static final Context.Key<UUID> CLIENT_ID =
            Context.key("userId");

    private GrpcSecurityContext() {}
}
