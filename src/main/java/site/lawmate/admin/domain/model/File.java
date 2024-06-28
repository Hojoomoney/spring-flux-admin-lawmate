package site.lawmate.admin.domain.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File {

    private String fileName;
    private byte[] fileData;
    private String fileType;
}
