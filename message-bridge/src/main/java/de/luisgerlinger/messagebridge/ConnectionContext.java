package de.luisgerlinger.messagebridge;

import de.luisgerlinger.grpc.IncomingMessage;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

@Getter
@Setter
public class ConnectionContext {
    private WebSocketSession webSocketSession;
    @Nullable
    private StreamObserver<IncomingMessage> grpcSendingObserver;

    public ConnectionContext(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }
}
