package de.luisgerlinger.chatserver;

import de.luisgerlinger.chatserver.boundary.grpc.MessageService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class ChatServer {
    public static final String SERVER_GRPC_HOST = "localhost";
    public static final int SERVER_GRPC_PORT    = 1234;

    public ChatServer() {
        Thread.ofVirtual().start(this::startChatServer);
    }

    @SneakyThrows
    private void startChatServer() {
        Server chatServer = ServerBuilder
                .forPort(SERVER_GRPC_PORT)
                .addService(new MessageService())
                .build();
        chatServer.start();
        log.info("ChatServer listening of port " + SERVER_GRPC_PORT);
        chatServer.awaitTermination();
    }
}
