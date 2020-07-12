package andry.hais.hp_store.repository;


import andry.hais.hp_store.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Integer> {
}
