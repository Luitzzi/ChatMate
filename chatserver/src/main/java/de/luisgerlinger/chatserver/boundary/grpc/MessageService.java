package de.luisgerlinger.chatserver.boundary.grpc;

import de.othr.vs.grpc.Message;
import de.othr.vs.grpc.MessageServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MessageService extends MessageServiceGrpc.MessageServiceImplBase {
    private final Map<UUID, StreamObserver<Message>> responseObserversByClientId = new ConcurrentHashMap<>();

    @Override
    public StreamObserver<Message> sendMessage(StreamObserver<Message> responseObserver) {
        UUID clientId = GrpcSecurityContext.CLIENT_ID.get();
        registerClient(clientId, responseObserver);
        return new ConnectionHandler(this, clientId);
    }

    public synchronized void disconnectClient(UUID clientId) {
        responseObserversByClientId.remove(clientId);
    }

    public synchronized void handleMessage(Message message) {
        switch (message.getType()) {
            case GLOBAL -> broadcastMessage(message);
            case PRIVATE -> forwardMessage(message);
        }
    }

    private void broadcastMessage(Message message) {
        responseObserversByClientId.entrySet().stream()
                .filter(entry -> {
                    UUID senderId = UUID.fromString(message.getSenderId());
                    return !entry.getKey().equals(senderId);})
                .forEach(entry -> entry.getValue().onNext(message));
    }

    private void forwardMessage(Message message) {
        StreamObserver<Message> responseObserver = responseObserversByClientId
                .get(UUID.fromString(message.getReceiverId()));
        responseObserver.onNext(message);
    }

    private void registerClient(UUID clientId, StreamObserver<Message> responseObserver) {
        responseObserversByClientId.put(clientId, responseObserver);
    }
}
