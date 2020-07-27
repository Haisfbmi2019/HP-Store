package andry.hais.shopapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@DynamicUpdate
public class ProductInOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderMain orderMain;

    @NotEmpty
    private String productId;

    @NotEmpty
    private String pictureUrl;

    @NotEmpty
    private String categoryType;

    @NotEmpty
    private String productType;

    @NotNull
    private String productDescription;

    @NotNull
    private String productMaterial;

    @NotNull
    private String productSizes;

    @NotNull
    private BigDecimal productPrice;

    @Min(1)
    private Integer count;

    public ProductInOrder(ProductInfo productInfo, String productSize, Integer quantity) {
        this.productId = productInfo.getProductId();
        this.pictureUrl = productInfo.getPictureUrl();
        this.categoryType = productInfo.getCategoryType();
        this.productType = productInfo.getProductType();
        this.productDescription = productInfo.getProductDescription();
        this.productMaterial = productInfo.getProductMaterial();
        this.productSizes = productSize;
        this.productPrice = productInfo.getProductFinalPrice();
        this.count = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInOrder that = (ProductInOrder) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(pictureUrl, that.pictureUrl) &&
                Objects.equals(categoryType, that.categoryType) &&
                Objects.equals(productType, that.productType) &&
                Objects.equals(productDescription, that.productDescription) &&
                Objects.equals(productMaterial, that.productMaterial) &&
                Objects.equals(productSizes, that.productSizes) &&
                Objects.equals(productPrice, that.productPrice) &&
                Objects.equals(count, that.count);
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, productId, pictureUrl, categoryType, productType, productDescription, productMaterial, productSizes, productPrice, count);
    }
}
