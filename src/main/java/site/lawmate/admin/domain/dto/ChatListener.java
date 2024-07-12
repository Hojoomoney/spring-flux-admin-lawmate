package site.lawmate.admin.domain.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import site.lawmate.admin.domain.model.ChatMessage;

@Component
@RequiredArgsConstructor
public class ChatListener {

    private final Sinks.Many<ChatMessage> chatSink = Sinks.many().multicast().onBackpressureBuffer();

    @KafkaListener(topics = "advice", groupId = "chat-group")
    public void listen(ChatMessage chatMessage) {
        chatSink.tryEmitNext(chatMessage);
    }

    public Flux<ChatMessage> getChatMessages(String roomId) {
        return chatSink.asFlux()
                .filter(chatMessage -> chatMessage.getRoomId().equals(roomId));
    }
}
