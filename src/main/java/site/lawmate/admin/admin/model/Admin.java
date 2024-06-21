package site.lawmate.admin.admin.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Document
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable {

    @Id
    private String id;
    private String username;
    private String password;
    private String role;

    private Boolean enabled;

}