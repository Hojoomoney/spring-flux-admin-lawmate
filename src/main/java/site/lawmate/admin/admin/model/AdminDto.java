package site.lawmate.admin.admin.model;

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
    private String password;
    private String role;
    private Boolean enabled = false;

}
