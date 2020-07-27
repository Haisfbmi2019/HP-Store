package andry.hais.shopapi.repository;


import andry.hais.shopapi.entity.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotEmpty;

public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, Long> {
    ProductInOrder findByProductId(@NotEmpty String productId);
}
