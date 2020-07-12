package andry.hais.hp_store.service;


import andry.hais.hp_store.dto.ProductInfoDTO;
import andry.hais.hp_store.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    Page<ProductInfo> findByCategory(String categoryType, String sort, Pageable pageable);

    Page<ProductInfo> findByCategoryAndType(String categoryType, String productType, String sort, Pageable pageable);

    void addProducts(List<ProductInfoDTO> products);

    // increase stock
    void increaseStock(String productId, Integer amount);

    //decrease stock
    void decreaseStock(String productId, Integer amount);

    ProductInfo update(ProductInfo productInfo);

    ProductInfo save(ProductInfo productInfo);

    void delete(String productId);

}
