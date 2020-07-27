package andry.hais.shopapi.service.impl;

import andry.hais.shopapi.component.EmailSender;
import andry.hais.shopapi.entity.Cart;
import andry.hais.shopapi.entity.OrderMain;
import andry.hais.shopapi.entity.ProductInOrder;
import andry.hais.shopapi.entity.User;
import andry.hais.shopapi.repository.CartRepository;
import andry.hais.shopapi.repository.OrderRepository;
import andry.hais.shopapi.repository.ProductInOrderRepository;
import andry.hais.shopapi.repository.UserRepository;
import andry.hais.shopapi.service.CartService;
import andry.hais.shopapi.service.ProductService;
import andry.hais.shopapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;


@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductService productService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductInOrderRepository productInOrderRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserService userService;
    @Autowired
    EmailSender emailSender;

    @Override
    public Cart getCart(User user) {
        return user.getCart();
    }

    @Override
    public ProductInOrder findByProductId(String productId) {
        return productInOrderRepository.findByProductId(productId);
    }

    @Override
    @Transactional
    public void mergeLocalCart(Collection<ProductInOrder> productInOrders, User user) {
        Cart finalCart = user.getCart();
        productInOrders.forEach(productInOrder -> {
            Set<ProductInOrder> set = finalCart.getProducts();
            Optional<ProductInOrder> old = set.stream().filter(e -> e.getProductId().equals(productInOrder.getProductId())).findFirst();
            ProductInOrder prod;
            if (old.isPresent()) {
                prod = old.get();
                prod.setCount(productInOrder.getCount() + prod.getCount());
            } else {
                prod = productInOrder;
                prod.setCart(finalCart);
                finalCart.getProducts().add(prod);
            }
            productInOrderRepository.save(prod);
        });
        cartRepository.save(finalCart);
    }

    @Override
    @Transactional
    public void delete(String itemId, User user) {
        Optional<ProductInOrder> op = user.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProductId())).findFirst();
        op.ifPresent(productInOrder -> {
            productInOrder.setCart(null);
            productInOrderRepository.deleteById(productInOrder.getId());
        });
    }

    @Override
    @Transactional
    public OrderMain checkout(User user) {
        // Creat an order
        OrderMain order = new OrderMain(user);
        orderRepository.save(order);

        // clear cart's foreign key & set order's foreign key& decrease stock
        user.getCart().getProducts().forEach(productInOrder -> {
            productInOrder.setCart(null);
            productInOrder.setOrderMain(order);
            productService.decreaseStock(productInOrder.getProductId(), productInOrder.getCount());
            productInOrderRepository.save(productInOrder);
        });

        String message = "Ваше замовлення успішно оброблено! Номер вашого замовлення № " +
                order.getOrderId() + " , загальною сумою - " + order.getOrderAmount() +
                " грн, дякуємо! В найближчому часі вам зателефонує наш менеджер!";
        emailSender.send(user.getEmail(), "Магазин HP-Store", message);
        return order;
    }
}
