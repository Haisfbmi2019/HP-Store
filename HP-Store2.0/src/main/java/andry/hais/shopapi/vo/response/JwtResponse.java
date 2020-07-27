package andry.hais.shopapi.vo.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String email;
    private String name;
    private String role;

    public JwtResponse(String token, String email, String name, String role) {
        this.email = email;
        this.name = name;
        this.token = token;
        this.role = role;
    }
}
