package site.lawmate.admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto implements Serializable {

    private String id;
    private String username;
    private String email;
    private String password;
    private String role;

    @Builder.Default
    private Boolean enabled = false;

}
