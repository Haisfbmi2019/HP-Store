package andry.hais.hp_store.service;

import andry.hais.hp_store.entity.Cart;
import andry.hais.hp_store.entity.OrderMain;
import andry.hais.hp_store.entity.ProductInOrder;
import andry.hais.hp_store.entity.User;

import java.util.Collection;

public interface CartService {
    Cart getCart(User user);

    ProductInOrder findByProductId(String productId);

    void mergeLocalCart(Collection<ProductInOrder> productInOrders, User user);

    void delete(String itemId, User user);

    OrderMain checkout(User user);
}
