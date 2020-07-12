package andry.hais.hp_store.entity;

import andry.hais.hp_store.dto.ProductInfoDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@DynamicUpdate
public class ProductInfo implements Serializable {
    @Id
    private String productId;

    @NotNull
    private String pictureUrl;

    @NotNull
    private String categoryType;

    @NotNull
    private String productType;// T-Shirt,...

    @NotNull
    private String productDescription;

    @NotNull
    private String productMaterial;

    @NotNull
    private String productSizes;

    @NotNull
    @Min(0)
    private BigDecimal productPrice;

    @NotNull
    @Min(0)
    private Integer productStock;

    /**
     * 1: on-sale 0: off-sale
     */
    @ColumnDefault("1")
    private Integer productStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @CreationTimestamp
    private LocalDateTime createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    private LocalDateTime updateTime;

    public ProductInfo() {
    }

    public ProductInfo(String productId, String pictureUrl, String categoryType, String productType, String productDescription, String productMaterial, String productSizes,
                       BigDecimal productPrice, Integer productStock, Integer productStatus, LocalDateTime createTime, LocalDateTime updateTime) {
        this.productId = productId;
        this.pictureUrl = pictureUrl;
        this.categoryType = categoryType;
        this.productType = productType;
        this.productDescription = productDescription;
        this.productMaterial = productMaterial;
        this.productSizes = productSizes;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productStatus = productStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public static ProductInfo of(String productId, String pictureUrl, String categoryType, String productType, String productDescription,
                                 String productMaterial, String productSizes, BigDecimal productPrice, Integer productStock, Integer productStatus
    ) {
        return new ProductInfo(productId, pictureUrl, categoryType, productType, productDescription, productMaterial, productSizes, productPrice, productStock,
                productStatus, null, null);
    }

    public static ProductInfo fromDTO(ProductInfoDTO productDTO) {
        return ProductInfo.of(productDTO.getProductId(), productDTO.getPictureUrl(), productDTO.getCategoryType(), productDTO.getProductType(),
                productDTO.getProductDescription(), productDTO.getProductMaterial(), productDTO.getProductSizes(), productDTO.getProductPrice(),
                productDTO.getProductStock(), productDTO.getProductStatus());
    }
}
