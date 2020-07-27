package andry.hais.shopapi.service.impl;


import andry.hais.shopapi.dto.ProductInfoDTO;
import andry.hais.shopapi.dto.ProductShortInfoDTO;
import andry.hais.shopapi.entity.Comment;
import andry.hais.shopapi.entity.ProductInfo;
import andry.hais.shopapi.enums.ProductStatusEnum;
import andry.hais.shopapi.enums.ResultEnum;
import andry.hais.shopapi.exception.MyException;
import andry.hais.shopapi.repository.CommentsRepository;
import andry.hais.shopapi.repository.ProductInfoRepository;
import andry.hais.shopapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductInfoRepository productInfoRepository;
    @Autowired
    CommentsRepository commentsRepository;

    @Override
    public List<ProductShortInfoDTO> mergeProducts(List<ProductInfo> products) {
        List<ProductShortInfoDTO> list = new ArrayList<>();
        products.forEach((x) -> {
            ProductShortInfoDTO product = ProductInfo.toShortInfoDTO(x);
            //

            list.add(product);
        });
        return list;
    }

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.findByProductId(productId);
    }

    @Override
    public List<Comment> findAll() {
        return commentsRepository.findAll();
    }

    @Override
    public PageImpl<ProductShortInfoDTO> findByCategory(String categoryType, String sort, Pageable pageable) {
        if(sort.equals("Asc")) {
            Page<ProductInfo> page = productInfoRepository.findByCategoryTypeOrderByProductFinalPriceAsc(categoryType, pageable);
            return new PageImpl<>(mergeProducts(page.getContent()), pageable, page.getTotalElements());
        } else if (sort.equals("Desc")){
            Page<ProductInfo> page = productInfoRepository.findByCategoryTypeOrderByProductFinalPriceDesc(categoryType, pageable);
            return new PageImpl<>(mergeProducts(page.getContent()), pageable, page.getTotalElements());
        } else {
            Page<ProductInfo> page = productInfoRepository.findAllByCategoryType(categoryType, pageable);
            return new PageImpl<>(mergeProducts(page.getContent()), pageable, page.getTotalElements());
        }
    }

    @Override
    public PageImpl<ProductShortInfoDTO> findByCategoryAndType(String categoryType, String productType, String sort,Pageable pageable) {
        if(sort.equals("Asc")) {
            Page<ProductInfo> page = productInfoRepository.findAllByCategoryTypeAndProductTypeOrderByProductFinalPriceAsc(categoryType, productType, pageable);
            return new PageImpl<>(mergeProducts(page.getContent()), pageable, page.getTotalElements());
        } else if (sort.equals("Desc")){
            Page<ProductInfo> page = productInfoRepository.findAllByCategoryTypeAndProductTypeOrderByProductFinalPriceDesc(categoryType, productType, pageable);
            return new PageImpl<>(mergeProducts(page.getContent()), pageable, page.getTotalElements());
        } else {
            Page<ProductInfo> page = productInfoRepository.findAllByCategoryTypeAndProductType(categoryType, productType, pageable);
            return new PageImpl<>(mergeProducts(page.getContent()), pageable, page.getTotalElements());
        }
    }

    @Override
    public void addProducts(List<ProductInfoDTO> products) {
        products.forEach((x) -> {
            productInfoRepository.save(fromDTO(x));
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
    public ProductInfo fromDTO(ProductInfoDTO productInfoDTO) {
        ProductInfo product = ProductInfo.fromDTO(productInfoDTO);
        setValues(product);
        return product;
    }

    private void setValues(ProductInfo product) {
        if(product.getProductStock()<=0){
            product.setProductStatus(ProductStatusEnum.DOWN.getCode());
        } else {
            product.setProductStatus(ProductStatusEnum.UP.getCode());
        }
        if(product.getProductDiscount().equals(new BigDecimal("0"))){
            product.setProductFinalPrice(product.getProductPrice());
            product.setUserDiscount(true);
        } else {
            //subtracts the discount percentage from the cost
            product.setProductFinalPrice((product.getProductPrice().subtract(product.getProductPrice().multiply(
                    product.getProductDiscount().divide(new BigDecimal("100"))))).setScale(0,3));
            product.setUserDiscount(false);
        }
    }

    @Override
    public ProductInfo update(ProductInfo productInfo) {
        productInfoRepository.findById(productInfo.getProductId());
        setValues(productInfo);
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
