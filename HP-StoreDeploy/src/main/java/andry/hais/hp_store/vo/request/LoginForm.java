package andry.hais.hp_store.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
