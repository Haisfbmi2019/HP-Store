package andry.hais.shopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductShortInfoDTO {
    private String productId;
    private String pictureUrl;
    private String pictureUrl2;
    private String categoryType;
    private String productType;// T-Shirt,...
    private String productDescription;
    private String productMaterial;
    private String productSizes;
    private BigDecimal productPrice;
    private BigDecimal productDiscount;
    private BigDecimal productFinalPrice;
    private BigDecimal productStars;
    private Integer productStock;
    private Integer productStatus;

    public static ProductShortInfoDTO of(String productId, String pictureUrl, String pictureUrl2, String categoryType,
                                         String productType, String productDescription, String productMaterial,
                                         String productSizes,
                                         BigDecimal productPrice, BigDecimal productDiscount, BigDecimal productFinalPrice, Integer productStock, Integer productStatus) {
        return new ProductShortInfoDTO(
                productId, pictureUrl, pictureUrl2, categoryType, productType, productDescription, productMaterial, productSizes,
                productPrice, productDiscount, productFinalPrice, null, productStock, productStatus);
    }

}
