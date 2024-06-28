package site.lawmate.admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import site.lawmate.admin.domain.model.File;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class BoardDto {

    private String id;
    private String title;
    private String content;
    private String writer;
    private Long viewCount;
    private List<File> files;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
