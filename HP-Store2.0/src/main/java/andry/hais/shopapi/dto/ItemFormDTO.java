package andry.hais.shopapi.dto;

import andry.hais.shopapi.form.ItemForm;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class ItemFormDTO {
    @JsonAlias
    private ItemForm itemForm;
}
