package site.lawmate.admin.domain.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class FileDto {

    private String fileName;
    private byte[] fileData;
    private String fileType;

    private String originalFileName;
    private String uploadFileName;
    private String uploadFilePath;
    private String uploadFileUrl;
}
