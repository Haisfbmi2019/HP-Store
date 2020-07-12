package andry.hais.hp_store.dto;

import andry.hais.hp_store.form.ItemForm;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class ItemFormDTO {
    @JsonAlias
    private ItemForm itemForm;
}
