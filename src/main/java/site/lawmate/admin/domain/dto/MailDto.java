package site.lawmate.admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class MailDto {

        private List<String> recipients;
        private String subject;
        private String text;
        private String to;
}
