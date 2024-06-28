package site.lawmate.admin.domain.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {

        @Id
        private String id;
        private String title;
        private String content;
        private String writer;
        private Long viewCount;
        private List<File> files;

        @CreatedDate
        private LocalDateTime createdDate;

        @LastModifiedDate
        private LocalDateTime modifiedDate;
}
