package andry.hais.shopapi.entity;

import andry.hais.shopapi.dto.ProductInfoDTO;
import andry.hais.shopapi.dto.ProductShortInfoDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@DynamicUpdate
public class ProductInfo implements Serializable {
    @Id
    private String productId;

    @NotNull
    private String pictureUrl;

    @NotNull
    private String pictureUrl2;

    @NotNull
    private String pictureUrl3;

    @NotNull
    private String categoryType;//men-home,...

    @NotNull
    private String productType;// T-Shirt,...

    @NotNull
    private String productDescription;

    @NotNull
    private String productMaterial;

    @NotNull
    @Column(length = 1000)
    private String productShortInformation;

    @NotNull
    private String productSizes;

    @NotNull
    @Min(0)
    private BigDecimal productPrice;

    @Min(0)
    private BigDecimal productDiscount;

    @NotNull
    @Min(0)
    private BigDecimal productFinalPrice;

    @Min(0)
    @Max(5)
    private BigDecimal productStars;

    @NotNull
    private Boolean userDiscount;

    @NotNull
    @Min(0)
    private Integer productStock;

    /**
     * 1: on-sale 0: off-sale
     */
    @ColumnDefault("1")
    private Integer productStatus;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @CreationTimestamp
    private LocalDateTime createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    private LocalDateTime updateTime;

    public ProductInfo(String productId, String pictureUrl, String pictureUrl2, String pictureUrl3, String categoryType,
                       String productType, String productDescription, String productMaterial, String productShortInformation,
                       String productSizes, BigDecimal productPrice, BigDecimal productDiscount, BigDecimal productFinalPrice, BigDecimal productStars,
                       Boolean userDiscount,
                       Integer productStock, Integer productStatus, LocalDateTime createTime, LocalDateTime updateTime) {
        this.productId = productId;
        this.pictureUrl = pictureUrl;
        this.pictureUrl2 = pictureUrl2;
        this.pictureUrl3 = pictureUrl3;
        this.categoryType = categoryType;
        this.productType = productType;
        this.productDescription = productDescription;
        this.productMaterial = productMaterial;
        this.productShortInformation = productShortInformation;
        this.productSizes = productSizes;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.productFinalPrice = productFinalPrice;
        this.productStars = productStars;
        this.userDiscount = userDiscount;
        this.productStock = productStock;
        this.productStatus = productStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public static ProductInfo of(String productId, String pictureUrl, String pictureUrl2, String pictureUrl3, String categoryType,
                                 String productType, String productDescription, String productMaterial, String productShortInformation, String productSizes,
                                 BigDecimal productPrice, BigDecimal productDiscount, Integer productStock, Integer productStatus) {
        return new ProductInfo(productId, pictureUrl, pictureUrl2, pictureUrl3, categoryType, productType, productDescription,
                productMaterial, productShortInformation, productSizes, productPrice, productDiscount, null, null, null, productStock,
                productStatus, null, null);
    }

    public static ProductInfo fromDTO(ProductInfoDTO productDTO) {
        return ProductInfo.of(productDTO.getProductId(), productDTO.getPictureUrl(), productDTO.getPictureUrl2(), productDTO.getPictureUrl3(),
                productDTO.getCategoryType(), productDTO.getProductType(), productDTO.getProductDescription(), productDTO.getProductMaterial(),
                productDTO.getProductShortInformation(),
                productDTO.getProductSizes(), productDTO.getProductPrice(), productDTO.getProductDiscount(),
                productDTO.getProductStock(), productDTO.getProductStatus());
    }

    public static ProductShortInfoDTO toShortInfoDTO(ProductInfo productInfo) {
        return ProductShortInfoDTO.of(productInfo.getProductId(), productInfo.getPictureUrl(), productInfo.getPictureUrl2(),
                productInfo.getCategoryType(), productInfo.getProductType(), productInfo.getProductDescription(), productInfo.getProductMaterial(),
                productInfo.getProductSizes(), productInfo.getProductPrice(), productInfo.getProductDiscount(), productInfo.getProductFinalPrice(),
                productInfo.getProductStock(), productInfo.getProductStatus()
        );
    }
}
