package site.lawmate.admin.domain.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat")
public class ChatMessage implements Serializable {

        private String roomId;
        private String sender;
        private String receiver;
        private String message;

        @CreatedDate
        private LocalDateTime timestamp;
}
