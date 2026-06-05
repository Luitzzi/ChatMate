package de.luisgerlinger.chatserver.boundary.grpc;

import de.luisgerlinger.grpc.Message;
import io.grpc.stub.StreamObserver;

import java.util.UUID;

public class ConnectionHandler implements StreamObserver<Message> {
    private final MessageService messageService;
    private final UUID clientId;

    public ConnectionHandler(MessageService messageService, UUID clientId) {
        this.messageService = messageService;
        this.clientId = clientId;
    }

    @Override
    public void onNext(Message value) {
        messageService.handleMessage(value);
    }

    @Override
    public void onError(Throwable t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onCompleted() {
        messageService.disconnectClient(clientId);
    }
}
