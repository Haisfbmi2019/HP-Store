package andry.hais.shopapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProductInfoDTO {

    private String productId;
    private String pictureUrl;
    private String pictureUrl2;
    private String pictureUrl3;
    private String categoryType;
    private String productType;// T-Shirt,...
    private String productDescription;
    private String productMaterial;
    private String productShortInformation;
    private String productSizes;
    private BigDecimal productPrice;
    private BigDecimal productDiscount;
    private BigDecimal productStars;
    private Boolean userDiscount;
    private Integer productStock;
    private Integer productStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    public static ProductInfoDTO of(String productId, String pictureUrl, String pictureUrl2, String pictureUrl3, String categoryType,
                                    String productType, String productDescription, String productMaterial, String productShortInformation,
                                    String productSizes,
                                    BigDecimal productPrice, BigDecimal productDiscount, Integer productStock) {
        return new ProductInfoDTO(
                productId, pictureUrl, pictureUrl2, pictureUrl3, categoryType, productType, productDescription, productMaterial,
                productShortInformation, productSizes,
                productPrice, productDiscount, null, null, productStock, null, null, null);
    }

}
