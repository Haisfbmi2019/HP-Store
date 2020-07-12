package andry.hais.hp_store.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductInfoDTO {

    private String productId;
    private String pictureUrl;
    private String categoryType;
    private String productType;// T-Shirt,...
    private String productDescription;
    private String productMaterial;
    private String productSizes;
    private BigDecimal productPrice;
    private Integer productStock;
    private Integer productStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    private ProductInfoDTO(String productId, String pictureUrl, String categoryType, String productType, String productDescription,
                           String productMaterial, String productSizes, BigDecimal productPrice, Integer productStock, Integer productStatus,
                           LocalDateTime createTime, LocalDateTime updateTime) {
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

    public static ProductInfoDTO of(String productId, String pictureUrl, String categoryType, String productType, String productDescription,
                                    String productMaterial, String productSizes, BigDecimal productPrice, Integer productStock, Integer productStatus
                                   ) {
        return new ProductInfoDTO(productId, pictureUrl, categoryType, productType, productDescription, productMaterial, productSizes, productPrice, productStock,
                productStatus, null, null);
    }

}
