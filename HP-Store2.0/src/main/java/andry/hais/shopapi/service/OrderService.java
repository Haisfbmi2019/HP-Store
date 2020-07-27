package andry.hais.shopapi.service;


import andry.hais.shopapi.entity.OrderMain;
import andry.hais.shopapi.entity.ProductInOrder;
import andry.hais.shopapi.form.QuickOrderForm;
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
