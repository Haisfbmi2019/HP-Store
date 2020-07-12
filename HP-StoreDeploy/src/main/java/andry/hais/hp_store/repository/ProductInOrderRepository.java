package andry.hais.hp_store.repository;


import andry.hais.hp_store.entity.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotEmpty;

public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, Long> {
    ProductInOrder findByProductId(@NotEmpty String productId);
}
