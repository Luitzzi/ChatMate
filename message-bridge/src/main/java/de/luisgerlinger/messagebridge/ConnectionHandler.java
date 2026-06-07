package de.luisgerlinger.messagebridge;

import de.luisgerlinger.grpc.IncomingMessage;
import de.luisgerlinger.grpc.MessageServiceGrpc;
import de.luisgerlinger.grpc.MessageType;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ConnectionHandler extends TextWebSocketHandler {
    private final MessageMapper messageMapper;

    private final ConcurrentHashMap<String, ConnectionContext> connectionContextByWebsocketId;
    private final ManagedChannel channel;
    private final MessageServiceGrpc.MessageServiceStub grpcStub;

    public ConnectionHandler(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
        connectionContextByWebsocketId = new ConcurrentHashMap<>();
        channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        grpcStub = MessageServiceGrpc.newStub(channel);
    }

    @PreDestroy
    public void shutdown() {
        channel.shutdown();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Got connection request");
        registerConnection(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String webSocketSessionId = session.getId();
        IncomingMessage grpcMessage = messageMapper.getMessage(message.getPayload());
        if (grpcMessage != null) {
            ConnectionContext connectionContext = connectionContextByWebsocketId.get(webSocketSessionId);
            if (connectionContext != null) {
                if (grpcMessage.getType() == MessageType.AUTH) {
                    log.info("Registering the auth token");
                    MessageReceiver receivingObserver = new MessageReceiver(
                            this, session, messageMapper);
                    MessageServiceGrpc.MessageServiceStub authenticatedStub =
                            setupAuthHeader(grpcMessage.getMessage(), receivingObserver);
                    StreamObserver<IncomingMessage> sendingObserver = authenticatedStub.sendMessage(receivingObserver);
                    connectionContext.setGrpcSendingObserver(sendingObserver);
                } else {
                    log.info("Forwarding Message {} to the backend", message.getPayload());
                    if (connectionContext.getGrpcSendingObserver() != null) {
                        connectionContext.getGrpcSendingObserver()
                                .onNext(grpcMessage);
                    } else {
                        log.error("No sending observer is set. Cannot forward the message to the server");
                    }
                }
            }
        } else {
            log.error("Message cannot be send. Errors while parsing the message");
            // TODO: Send connection error
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        // TODO: Send connection closed message
        disconnectConnection(session.getId());
    }

    public void disconnectConnection(String webSocketId) {
        log.info("Disconnecting session {}", webSocketId);
        ConnectionContext connectionContext = connectionContextByWebsocketId
                .remove(webSocketId);
        if (connectionContext != null) {
            try {
                connectionContext.getWebSocketSession().close();
            } catch (IOException e) {
                log.error("Could not close the websocket connection with id {}.\n{}", webSocketId, e.getMessage());
            }
            if (connectionContext.getGrpcSendingObserver() != null)
                connectionContext.getGrpcSendingObserver().onCompleted();
        }
    }

    private void registerConnection(WebSocketSession webSocketSession) {
        String webSocketSessionId = webSocketSession.getId();
        log.info("Registering session {}", webSocketSessionId);
        ConnectionContext connectionContext = new ConnectionContext(webSocketSession);
        connectionContextByWebsocketId.put(webSocketSessionId, connectionContext);
    }

    private MessageServiceGrpc.MessageServiceStub setupAuthHeader(String token, MessageReceiver receivingObserver) {
        Metadata authMetadata = buildAuthMetadata(token);
        ClientInterceptor interceptor = MetadataUtils.newAttachHeadersInterceptor(authMetadata);
        return grpcStub.withInterceptors(interceptor);
    }

    private Metadata buildAuthMetadata(String token) {
        Metadata metadata = new Metadata();
        Metadata.Key<String> AUTH_TOKEN = Metadata.Key.of(
                "authorization", Metadata.ASCII_STRING_MARSHALLER);
        metadata.put(AUTH_TOKEN, "Bearer " + token);
        return metadata;
    }
}
