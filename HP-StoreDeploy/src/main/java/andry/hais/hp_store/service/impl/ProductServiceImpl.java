package andry.hais.hp_store.service.impl;


import andry.hais.hp_store.dto.ProductInfoDTO;
import andry.hais.hp_store.entity.ProductInfo;
import andry.hais.hp_store.enums.ProductStatusEnum;
import andry.hais.hp_store.enums.ResultEnum;
import andry.hais.hp_store.exception.MyException;
import andry.hais.hp_store.repository.ProductInfoRepository;
import andry.hais.hp_store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.findByProductId(productId);
    }

    @Override
    public Page<ProductInfo> findByCategory(String categoryType, String sort,Pageable pageable) {
        if(sort.equals("Asc")) {
            return productInfoRepository.findByCategoryTypeOrderByProductPriceAsc(categoryType, pageable);
        } else if (sort.equals("Desc")){
            return productInfoRepository.findByCategoryTypeOrderByProductPriceDesc(categoryType, pageable);
        } else {
            return productInfoRepository.findAllByCategoryType(categoryType, pageable);
        }
    }

    @Override
    public Page<ProductInfo> findByCategoryAndType(String categoryType, String productType, String sort,Pageable pageable) {
        if(sort.equals("Asc")) {
            return productInfoRepository.findAllByCategoryTypeAndProductTypeOrderByProductPriceAsc(categoryType, productType, pageable);
        } else if (sort.equals("Desc")){
            return productInfoRepository.findAllByCategoryTypeAndProductTypeOrderByProductPriceDesc(categoryType, productType, pageable);
        } else {
            return productInfoRepository.findAllByCategoryTypeAndProductType(categoryType, productType, pageable);
        }
    }

    @Override
    public void addProducts(List<ProductInfoDTO> products) {
        products.forEach((x) -> {
            ProductInfo product = ProductInfo.fromDTO(x);
            if(product.getProductStock()<=0){
                product.setProductStatus(ProductStatusEnum.DOWN.getCode());
            } else {
                product.setProductStatus(ProductStatusEnum.UP.getCode());
            }
            productInfoRepository.save(product);
        });
    }

    @Override
    @Transactional
    public void increaseStock(String productId, Integer amount) {
        ProductInfo productInfo = findOne(productId);
        if (productInfo == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

        Integer update = productInfo.getProductStock() + amount;
        productInfo.setProductStock(update);
        productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public void decreaseStock(String productId, Integer amount) {
        ProductInfo productInfo = findOne(productId);
        if (productInfo == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

        int update = productInfo.getProductStock() - amount;
        if(update < 0) throw new MyException(ResultEnum.PRODUCT_NOT_ENOUGH);
        if(update == 0) {
            productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        }
        productInfo.setProductStock(update);
        productInfoRepository.save(productInfo);
    }

    @Override
    public ProductInfo update(ProductInfo productInfo) {

        productInfoRepository.findById(productInfo.getProductId());
        if(productInfo.getProductStock()<=0){
            productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        } else {
            productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        }
        productInfo.setUpdateTime(LocalDateTime.now());
        return productInfoRepository.save(productInfo);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return update(productInfo);
    }

    @Override
    public void delete(String productId) {
        ProductInfo productInfo = findOne(productId);
        if (productInfo == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);
        productInfoRepository.delete(productInfo);

    }


}
