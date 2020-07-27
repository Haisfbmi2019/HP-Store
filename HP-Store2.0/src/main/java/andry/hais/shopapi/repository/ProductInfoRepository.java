package andry.hais.shopapi.repository;


import andry.hais.shopapi.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    ProductInfo findByProductId(String id);
    // product in one category
    Page<ProductInfo> findAllByCategoryType(String categoryType, Pageable pageable);

    Page<ProductInfo> findByCategoryTypeOrderByProductFinalPriceAsc(String categoryType, Pageable pageable);

    Page<ProductInfo> findByCategoryTypeOrderByProductFinalPriceDesc(String categoryType, Pageable pageable);

    Page<ProductInfo> findAllByCategoryTypeAndProductTypeOrderByProductFinalPriceAsc(String categoryType, String productType, Pageable pageable);

    Page<ProductInfo> findAllByCategoryTypeAndProductTypeOrderByProductFinalPriceDesc(String categoryType, String productType, Pageable pageable);

    Page<ProductInfo> findAllByCategoryTypeAndProductType(String categoryType, String productType, Pageable pageable);
}
