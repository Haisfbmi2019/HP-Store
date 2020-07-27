package andry.hais.shopapi.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class ItemForm {
    @NotEmpty
    private String productId;
    @NotEmpty
    private String productSize;
    @Min(value = 1)
    private Integer quantity;
}
