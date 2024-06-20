package site.lawmate.admin.admin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable {

    @Id
    private String id;
    private String username;
    private String password;
    private String role;

}