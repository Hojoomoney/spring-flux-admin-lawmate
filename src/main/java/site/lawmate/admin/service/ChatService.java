package site.lawmate.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.admin.domain.model.ChatMessage;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ReactiveKafkaProducerTemplate<String, ChatMessage> kafkaTemplate;
  //  private final ReactiveKafkaConsumerTemplate<String, ChatMessage> kafkaConsumerTemplate;
    private final ReactiveMongoTemplate mongoTemplate;

    public Mono<ChatMessage> processMessage(ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
        return kafkaTemplate.send("advice", chatMessage)
                .then(mongoTemplate.save(chatMessage))
                .thenReturn(chatMessage);
    }

    public Flux<ChatMessage> getChatMessages(String roomId) {
        Criteria criteria = Criteria.where("roomId").is(roomId);
        Query query = new Query(criteria);
        log.info("Query: {}", query);
        return mongoTemplate.find(query, ChatMessage.class);
    }

//    public Flux<ServerSentEvent<ChatMessage>> receiveMessages(String roomId) {
//        return kafkaConsumerTemplate.receive()
//                .map(ConsumerRecord::value)
//                .filter(ChatMessage -> ChatMessage.getRoomId().equals(roomId))
//                .map(message -> ServerSentEvent.builder(message).build());
//    }

    public Mono<ChatMessage> createChatRoom(String sender, String receiver) {
        ChatMessage chatMessage = ChatMessage.builder()
                .roomId(UUID.randomUUID().toString())
                .sender(sender)
                .receiver(receiver)
                .message("채팅방이 생성되었습니다. 대화를 시작해보세요!")
                .build();
        return mongoTemplate.save(chatMessage);
    }
}
