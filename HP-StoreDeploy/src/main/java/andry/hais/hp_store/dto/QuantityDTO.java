package andry.hais.hp_store.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class QuantityDTO {
    @JsonAlias
    private Integer quantity;
}
