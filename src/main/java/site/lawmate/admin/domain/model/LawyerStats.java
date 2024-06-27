package site.lawmate.admin.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LawyerStats {

    @Id
    private LocalDate date; // 날짜
    private Long newLawyerCount; // 신규 가입자 수
    private Long increaseRate; // 증가율
    private Long countLawyersFalse; // 인증 안된 변호사 수
}
