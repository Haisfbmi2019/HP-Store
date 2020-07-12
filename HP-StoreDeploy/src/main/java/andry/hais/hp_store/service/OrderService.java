package andry.hais.hp_store.service;


import andry.hais.hp_store.entity.OrderMain;
import andry.hais.hp_store.entity.ProductInOrder;
import andry.hais.hp_store.form.QuickOrderForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Collection;


public interface OrderService {
    Page<OrderMain> findAll(Pageable pageable);

    Page<OrderMain> findByBuyerEmail(String email, Pageable pageable);

    OrderMain findOne(Long orderId);

    OrderMain createQuickOrder(QuickOrderForm quickOrderForm, BigDecimal totalPrice);

    void mergeQuickOrder(Collection<ProductInOrder> productInOrders, OrderMain orderMain);

    OrderMain finish(Long orderId);

    OrderMain cancel(Long orderId);

}
