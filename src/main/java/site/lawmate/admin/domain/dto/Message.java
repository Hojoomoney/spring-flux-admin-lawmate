package site.lawmate.admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Message {

        private String message;
        private String code;
        private String status;

}
