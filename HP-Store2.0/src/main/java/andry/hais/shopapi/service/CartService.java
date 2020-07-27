package andry.hais.shopapi.service;

import andry.hais.shopapi.entity.Cart;
import andry.hais.shopapi.entity.OrderMain;
import andry.hais.shopapi.entity.ProductInOrder;
import andry.hais.shopapi.entity.User;
import andry.hais.shopapi.form.ItemForm;

import java.util.Collection;

public interface CartService {
    Cart getCart(User user);

    ProductInOrder findByProductId(String productId);

    void mergeLocalCart(Collection<ProductInOrder> productInOrders, User user);

    void delete(String itemId, User user);

    OrderMain checkout(User user);
}
