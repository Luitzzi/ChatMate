package de.luisgerlinger.messagebridge;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import de.luisgerlinger.grpc.Message;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageMapper {

    @Nullable
    public synchronized String getJson(Message message) {
        try {
            return JsonFormat.printer().print(message);
        } catch (InvalidProtocolBufferException e) {
            log.error("Cannot convert message {} into a json object.\n {}", message, e.getMessage());
            return null;
        }
    }

    @Nullable
    public synchronized Message getMessage(String json) {
        Message.Builder messageBuilder = Message.newBuilder();
        try {
            JsonFormat.parser()
                    .merge(json, messageBuilder);
            return messageBuilder.build();
        } catch (InvalidProtocolBufferException e) {
            log.error("Cannot convert json {} into grpc Message.\n{}", json, e.getMessage());
            return null;
        }
    }
}
