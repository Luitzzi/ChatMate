package de.luisgerlinger.chatserver.boundary.grpc;

import de.luisgerlinger.grpc.IncomingMessage;
import io.grpc.stub.StreamObserver;

import java.util.UUID;

public class ConnectionHandler implements StreamObserver<IncomingMessage> {
    private final MessageServiceImpl messageServiceImpl;
    private final UUID clientId;
    private final String clientName;

    public ConnectionHandler(MessageServiceImpl messageServiceImpl, UUID clientId, String clientName) {
        this.messageServiceImpl = messageServiceImpl;
        this.clientId = clientId;
        this.clientName = clientName;
    }

    @Override
    public void onNext(IncomingMessage value) {
        messageServiceImpl.handleMessage(value, clientId, clientName);
    }

    @Override
    public void onError(Throwable t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onCompleted() {
        messageServiceImpl.disconnectClient(clientId);
    }
}
