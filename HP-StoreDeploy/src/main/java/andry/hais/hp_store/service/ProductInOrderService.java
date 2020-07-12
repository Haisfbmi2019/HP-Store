package andry.hais.hp_store.service;

import andry.hais.hp_store.entity.ProductInOrder;
import andry.hais.hp_store.entity.User;

public interface ProductInOrderService {
    void update(String itemId, Integer quantity, User user);

    ProductInOrder findOne(String itemId, User user);
}
