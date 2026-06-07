package de.luisgerlinger.messagebridge;

import de.luisgerlinger.grpc.OutgoingMessage;
import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
public class MessageReceiver implements StreamObserver<OutgoingMessage> {
    private final ConnectionHandler connectionHandler;
    private final WebSocketSession webSocketSession;
    private final MessageMapper messageMapper;

    public MessageReceiver(
            ConnectionHandler connectionHandler,
            WebSocketSession webSocketSession,
            MessageMapper messageMapper
    ) {
        this.connectionHandler = connectionHandler;
        this.webSocketSession = webSocketSession;
        this.messageMapper = messageMapper;
    }

    @SneakyThrows
    @Override
    public void onNext(OutgoingMessage value) {
        log.info("Forwarding message {} to the client", value);
        String json = messageMapper.getJson(value);
        if (json != null) {
            webSocketSession.sendMessage(new TextMessage(json));
        }
    }

    @Override
    public void onError(Throwable t) {
        throw new UnsupportedOperationException("Errors not handled yet. Error: " + t.getMessage());
    }

    @Override
    public void onCompleted() {
        connectionHandler.disconnectConnection(webSocketSession.getId());
    }
}
