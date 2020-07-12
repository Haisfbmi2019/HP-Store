package andry.hais.hp_store.service.impl;


import andry.hais.hp_store.entity.OrderMain;
import andry.hais.hp_store.entity.ProductInOrder;
import andry.hais.hp_store.entity.ProductInfo;
import andry.hais.hp_store.enums.OrderStatusEnum;
import andry.hais.hp_store.enums.ResultEnum;
import andry.hais.hp_store.exception.MyException;
import andry.hais.hp_store.form.QuickOrderForm;
import andry.hais.hp_store.repository.OrderRepository;
import andry.hais.hp_store.repository.ProductInOrderRepository;
import andry.hais.hp_store.repository.ProductInfoRepository;
import andry.hais.hp_store.repository.UserRepository;
import andry.hais.hp_store.service.OrderService;
import andry.hais.hp_store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductInfoRepository productInfoRepository;
    @Autowired
    ProductService productService;
    @Autowired
    ProductInOrderRepository productInOrderRepository;

    @Override
    public Page<OrderMain> findAll(Pageable pageable) {
        return orderRepository.findAllByOrderByOrderStatusAscCreateTimeDesc(pageable);
    }

    @Override
    public Page<OrderMain> findByBuyerEmail(String email, Pageable pageable) {
        return orderRepository.findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(email, pageable);
    }

    @Override
    public OrderMain findOne(Long orderId) {
        OrderMain orderMain = orderRepository.findByOrderId(orderId);
        if (orderMain == null) {
            throw new MyException(ResultEnum.ORDER_NOT_FOUND);
        }
        return orderMain;
    }

    @Override
    @Transactional
    public OrderMain createQuickOrder(QuickOrderForm quickOrderForm, BigDecimal totalPrice) {
        OrderMain order = new OrderMain(quickOrderForm, totalPrice);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void mergeQuickOrder(Collection<ProductInOrder> productInOrders, OrderMain orderMain) {
        productInOrders.forEach(productInOrder -> {
            productInOrder.setCart(null);
            productInOrder.setOrderMain(orderMain);
            productService.decreaseStock(productInOrder.getProductId(), productInOrder.getCount());
            productInOrderRepository.save(productInOrder);
        });
    }

    @Override
    @Transactional
    public OrderMain finish(Long orderId) {
        OrderMain orderMain = findOne(orderId);
        if (!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderMain.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderRepository.save(orderMain);
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public OrderMain cancel(Long orderId) {
        OrderMain orderMain = findOne(orderId);
        if (!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderMain.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
        orderRepository.save(orderMain);

        // Restore Stock
        Iterable<ProductInOrder> products = orderMain.getProducts();
        for (ProductInOrder productInOrder : products) {
            ProductInfo productInfo = productInfoRepository.findByProductId(productInOrder.getProductId());
            if (productInfo != null) {
                productService.increaseStock(productInOrder.getProductId(), productInOrder.getCount());
            }
        }
        return orderRepository.findByOrderId(orderId);
    }
}
