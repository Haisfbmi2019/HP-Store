package andry.hais.shopapi.service.impl;


import andry.hais.shopapi.component.EmailSender;
import andry.hais.shopapi.entity.OrderMain;
import andry.hais.shopapi.entity.ProductInOrder;
import andry.hais.shopapi.entity.ProductInfo;
import andry.hais.shopapi.enums.OrderStatusEnum;
import andry.hais.shopapi.enums.ResultEnum;
import andry.hais.shopapi.exception.MyException;
import andry.hais.shopapi.form.QuickOrderForm;
import andry.hais.shopapi.repository.OrderRepository;
import andry.hais.shopapi.repository.ProductInOrderRepository;
import andry.hais.shopapi.repository.ProductInfoRepository;
import andry.hais.shopapi.repository.UserRepository;
import andry.hais.shopapi.service.OrderService;
import andry.hais.shopapi.service.ProductService;
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
    @Autowired
    OrderService orderService;
    @Autowired
    EmailSender emailSender;

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
        String message = "Ваше замовлення успішно оброблено! Номер вашого замовлення № " +
                order.getOrderId() + " , загальною сумою - " + totalPrice +
                " грн, дякуємо! В найближчому часі вам зателефонує наш менеджер!";
        emailSender.send(quickOrderForm.getEmail(), "Магазин HP-Store", message);
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
