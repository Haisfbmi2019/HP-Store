package andry.hais.hp_store.repository;


import andry.hais.hp_store.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    ProductInfo findByProductId(String id);
    // product in one category
    Page<ProductInfo> findAllByCategoryType(String categoryType, Pageable pageable);

    Page<ProductInfo> findByCategoryTypeOrderByProductPriceAsc(String categoryType, Pageable pageable);

    Page<ProductInfo> findByCategoryTypeOrderByProductPriceDesc(String categoryType, Pageable pageable);

    Page<ProductInfo> findAllByCategoryTypeAndProductTypeOrderByProductPriceAsc( String categoryType, String productType, Pageable pageable);

    Page<ProductInfo> findAllByCategoryTypeAndProductTypeOrderByProductPriceDesc( String categoryType, String productType, Pageable pageable);

    Page<ProductInfo> findAllByCategoryTypeAndProductType( String categoryType, String productType, Pageable pageable);
}
