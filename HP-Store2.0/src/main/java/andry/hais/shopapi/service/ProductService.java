package andry.hais.shopapi.service;


import andry.hais.shopapi.dto.ProductInfoDTO;
import andry.hais.shopapi.dto.ProductShortInfoDTO;
import andry.hais.shopapi.entity.Comment;
import andry.hais.shopapi.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<ProductShortInfoDTO> mergeProducts(List<ProductInfo> products);

    ProductInfo findOne(String productId);

    List<Comment> findAll();

    PageImpl<ProductShortInfoDTO> findByCategory(String categoryType, String sort, Pageable pageable);

    PageImpl<ProductShortInfoDTO> findByCategoryAndType(String categoryType, String productType, String sort, Pageable pageable);

    void addProducts(List<ProductInfoDTO> products);

    // increase stock
    void increaseStock(String productId, Integer amount);

    //decrease stock
    void decreaseStock(String productId, Integer amount);

    ProductInfo fromDTO(ProductInfoDTO productInfoDTO);

    ProductInfo update(ProductInfo productInfo);

    ProductInfo save(ProductInfo productInfo);

    void delete(String productId);

}
