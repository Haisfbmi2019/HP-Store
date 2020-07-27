package andry.hais.shopapi.api;

import andry.hais.shopapi.dto.ProductInfoDTO;
import andry.hais.shopapi.dto.ProductShortInfoDTO;
import andry.hais.shopapi.entity.ProductInfo;
import andry.hais.shopapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/collection/{categoryType}")
    public PageImpl<ProductShortInfoDTO> findByCategory(@PathVariable String categoryType,
                                                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                        @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                        @RequestParam(value = "sort", defaultValue = "null") String sort) {
        PageRequest request = PageRequest.of(page - 1, size);
        return productService.findByCategory(categoryType, sort, request);
    }

    @GetMapping("/collection/{categoryType}/{productType}")
    public PageImpl<ProductShortInfoDTO> findByCategoryAndType(@PathVariable String productType,
                                                               @PathVariable String categoryType,
                                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                               @RequestParam(value = "sort", defaultValue = "null") String sort) {
        PageRequest request = PageRequest.of(page - 1, size);
        return productService.findByCategoryAndType(categoryType, productType, sort, request);
    }

    @GetMapping("/collection/product/{productId}")
    public ProductInfo showOne(@PathVariable("productId") String productId) {

        return productService.findOne(productId);
    }

    @PostMapping("/admin/product/new")
    public ResponseEntity create(@Valid @RequestBody ProductInfoDTO product,
                                 BindingResult bindingResult) {
        ProductInfo productIdExists = productService.findOne(product.getProductId());
        if (productIdExists != null) {
            bindingResult
                    .rejectValue("productId", "error.product",
                            "There is already a product with the code provided");
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }
        return ResponseEntity.ok(productService.save(productService.fromDTO(product)));
    }

    @PutMapping("/admin/product/{id}/edit")
    public ResponseEntity edit(@PathVariable("id") String productId,
                               @Valid @RequestBody ProductInfo product,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }
        if (!productId.equals(product.getProductId())) {
            return ResponseEntity.badRequest().body("Id Not Matched");
        }

        return ResponseEntity.ok(productService.update(product));
    }

    @DeleteMapping("/admin/product/{id}/delete")
    public ResponseEntity delete(@PathVariable("id") String productId) {
        productService.delete(productId);
        return ResponseEntity.ok().build();
    }
}
