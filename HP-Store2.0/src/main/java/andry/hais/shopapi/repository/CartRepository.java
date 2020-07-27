package andry.hais.shopapi.repository;


import andry.hais.shopapi.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Integer> {
}
