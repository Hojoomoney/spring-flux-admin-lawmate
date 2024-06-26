package site.lawmate.admin.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Announce {

    @Id
    private String id;
    private String title;
    private String content;
    private String writer;
    private String regDate;
}
