package site.lawmate.admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class LawyerStatsDto {

    private Long totalLawyers;
    private Long totalLawyersAuthFalse;
    private Long totalLawyersAuthTrue;

    private LocalDate date; // 날짜
    private Long newLawyerCount; // 신규 가입자 수
    private Long increaseRate; // 증가율

}
