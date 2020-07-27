package andry.hais.shopapi.form;

import andry.hais.shopapi.dto.QuickOrderFormDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class QuickOrderForm {

    @NotEmpty
    private String email;

    @NotEmpty
    private String name;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String city;

    @NotEmpty
    private String address;

    public static QuickOrderForm of(String email, String name, String phone, String city, String address) {
        return new QuickOrderForm(email, name, phone, city, address);
    }

    public static QuickOrderForm fromDTO(QuickOrderFormDTO quickOrderFormDTO) {
        return QuickOrderForm.of(quickOrderFormDTO.getEmail(), quickOrderFormDTO.getName(), quickOrderFormDTO.getPhone(), quickOrderFormDTO.getCity(),
                quickOrderFormDTO.getAddress());
    }
}
