package site.lawmate.admin.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.admin.domain.model.ChatMessage;
import site.lawmate.admin.service.ChatService;

@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler implements WebSocketHandler {
    private final ChatService chatService;
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Flux<WebSocketMessage> messageFlux = session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(payload -> {
                    ChatMessage chatMessage = null;
                    try {
                        chatMessage = new ObjectMapper().readValue(payload, ChatMessage.class);
                    } catch (JsonProcessingException e) {
                        return Flux.error(new RuntimeException(e));
                    }
                    return chatService.processMessage(chatMessage);
                })
                .handle((chatMessage, sink) -> {
                    try {
                        sink.next(session.textMessage(new ObjectMapper().writeValueAsString(chatMessage)));
                    } catch (JsonProcessingException e) {
                        sink.error(new RuntimeException(e));
                    }
                });
        return session.send(messageFlux);
    }
}
