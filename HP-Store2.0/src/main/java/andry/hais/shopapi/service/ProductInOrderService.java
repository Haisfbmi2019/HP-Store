package andry.hais.shopapi.service;

import andry.hais.shopapi.entity.ProductInOrder;
import andry.hais.shopapi.entity.User;

public interface ProductInOrderService {
    void update(String itemId, Integer quantity, User user);

    ProductInOrder findOne(String itemId, User user);
}
