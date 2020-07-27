package andry.hais.shopapi.dto;

import andry.hais.shopapi.form.ItemForm;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class QuickOrderFormDTO {

    @JsonAlias
    private List<ItemForm> products;

    @JsonAlias
    private String email;

    @JsonAlias
    private String name;

    @JsonAlias
    private String phone;

    @JsonAlias
    private String city;

    @JsonAlias
    private String address;

    @JsonAlias
    private Integer totalPrice;
}
