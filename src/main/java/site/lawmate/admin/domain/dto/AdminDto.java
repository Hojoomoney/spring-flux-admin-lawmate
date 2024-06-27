package site.lawmate.admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AdminDto implements Serializable {

    private String id;
    private String username;
    private String name;
    private String email;
    private String password;
    private String role;

    @Builder.Default
    private Boolean enabled = false;

}
