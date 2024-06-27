package site.lawmate.admin.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "lawyers")
@Builder
@AllArgsConstructor
@ToString(exclude = "id")
@NoArgsConstructor
public class Lawyer {
    @Id
    String id;
    String username;
    String email;
    String password;
    String name;
    String phone;
    String birth;
    String lawyerNo;
    String mid;
    Boolean auth;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
