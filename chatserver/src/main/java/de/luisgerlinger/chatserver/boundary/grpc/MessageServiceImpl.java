package de.luisgerlinger.chatserver.boundary.grpc;

import de.luisgerlinger.chatserver.service.UserBA;
import de.luisgerlinger.chatserver.service.dto.UserUiDTO;
import de.luisgerlinger.grpc.IncomingMessage;
import de.luisgerlinger.grpc.MessageServiceGrpc;
import de.luisgerlinger.grpc.OutgoingMessage;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class MessageServiceImpl extends MessageServiceGrpc.MessageServiceImplBase {
    private final Map<UUID, StreamObserver<OutgoingMessage>> responseObserversByClientId = new ConcurrentHashMap<>();
    private final UserBA userBA;

    @Override
    public StreamObserver<IncomingMessage> sendMessage(StreamObserver<OutgoingMessage> responseObserver) {
        UUID clientId = GrpcSecurityContext.CLIENT_ID.get();
        registerClient(clientId, responseObserver);
        String clientName = userBA.getUserById(clientId)
                .map(UserUiDTO::getUsername)
                .orElseThrow(() -> new NoSuchElementException("User with id " + clientId + " does not exist!"));
        return new ConnectionHandler(this, clientId, clientName);
    }

    public synchronized void disconnectClient(UUID clientId) {
        responseObserversByClientId.remove(clientId);
    }

    public synchronized void handleMessage(
            IncomingMessage message, UUID senderId, String senderName
    ) {
        OutgoingMessage outgoingMessage = OutgoingMessage.newBuilder()
                .setType(message.getType())
                .setSenderId(senderId.toString())
                .setSenderName(senderName)
                .setTargetId(message.getTargetId())
                .setMessage(message.getMessage())
                .setTimestamp(message.getTimestamp())
                .build();
        switch (message.getType()) {
            case GLOBAL -> broadcastMessage(outgoingMessage, senderId);
            case PRIVATE -> forwardMessage(outgoingMessage);
        }
    }

    private void broadcastMessage(OutgoingMessage message, UUID senderId) {
        log.info("Broadcasting message {} from {}", message.getMessage(), message.getSenderName());
        responseObserversByClientId.entrySet().stream()
                .filter(entry ->
                        !entry.getKey()
                                .equals(senderId))
                .forEach(entry ->
                        entry.getValue()
                            .onNext(message));
    }

    private void forwardMessage(OutgoingMessage message) {
        if (message.getTargetId() == null) {
            log.error("Trying to send a private message without a defined receiver");
            return;
        }

        log.info("Forwarding message {} to {}", message.getMessage(), message.getTargetId());
        StreamObserver<OutgoingMessage> responseObserver = responseObserversByClientId
                .get(UUID.fromString(message.getTargetId()));
        responseObserver.onNext(message);
    }

    private void registerClient(UUID clientId, StreamObserver<OutgoingMessage> responseObserver) {
        log.info("Registering client: {}", clientId);
        responseObserversByClientId.put(clientId, responseObserver);
    }
}
