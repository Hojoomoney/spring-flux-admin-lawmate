package site.lawmate.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.admin.domain.dto.ChatListener;
import site.lawmate.admin.domain.model.ChatMessage;
import site.lawmate.admin.service.ChatService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final ChatListener chatListener;
    @GetMapping("/messages")
    public Flux<ServerSentEvent<ChatMessage>> getMessages(@RequestParam String roomId) {
        return chatListener.getChatMessages(roomId)
                .map(message -> ServerSentEvent.builder(message).build());
    }

    @PostMapping("/send")
    public Mono<Void> sendMessage(@RequestBody ChatMessage chatMessage) {
        return chatService.processMessage(chatMessage).then();
    }

//    @GetMapping("/receive")
//    public Flux<ServerSentEvent<ChatMessage>> receiveMessages(@RequestParam String roomId) {
//        return chatService.receiveMessages(roomId);
//    }

    @GetMapping("/create")
    public Mono<ChatMessage> createChatRoom(@RequestParam String sender, @RequestParam String receiver) {
        return chatService.createChatRoom(sender, receiver);
    }

    @GetMapping("/history")
    public Flux<ChatMessage> getChatHistory(@RequestParam String roomId) {
        return chatService.getChatMessages(roomId);
    }
}
