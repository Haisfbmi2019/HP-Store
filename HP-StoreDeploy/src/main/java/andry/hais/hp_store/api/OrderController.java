package andry.hais.hp_store.api;


import andry.hais.hp_store.component.EmailSender;
import andry.hais.hp_store.dto.QuickOrderFormDTO;
import andry.hais.hp_store.entity.OrderMain;
import andry.hais.hp_store.entity.ProductInOrder;
import andry.hais.hp_store.form.QuickOrderForm;
import andry.hais.hp_store.repository.OrderRepository;
import andry.hais.hp_store.repository.ProductInOrderRepository;
import andry.hais.hp_store.service.OrderService;
import andry.hais.hp_store.service.ProductService;
import andry.hais.hp_store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;


@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductInOrderRepository productInOrderRepository;
    @Autowired
    EmailSender emailSender;

    @PostMapping("")
    public ResponseEntity<OrderMain> mergeOrder(@RequestBody Collection<ProductInOrder> productInOrders, OrderMain orderMain) {
        try {
            orderService.mergeQuickOrder(productInOrders, orderMain);
        } catch (Exception e) {
            ResponseEntity.badRequest().body("Merge Order Failed");
        }
        return ResponseEntity.ok(orderMain);
    }

    @PostMapping("/quickOrder")
    public ResponseEntity<OrderMain> quickOrder(@RequestBody QuickOrderFormDTO quickOrderFormDTO) {
        OrderMain orderMain = orderService.createQuickOrder(QuickOrderForm.fromDTO(quickOrderFormDTO), BigDecimal.valueOf(quickOrderFormDTO.getTotalPrice()));
        quickOrderFormDTO.getProducts().forEach(itemForm -> {
            mergeOrder(Collections.singleton(new ProductInOrder(productService.findOne(itemForm.getProductId()), itemForm.getProductSize(), itemForm.getQuantity())), orderMain);
        });
        orderService.finish(orderMain.getOrderId());
        String message = "Ваше замовлення успішно оброблено! Номер вашого замовлення № " +
                orderMain.getOrderId() + " , загальною сумою - " + quickOrderFormDTO.getTotalPrice() +
                " грн, дякуємо! В найближчому часі вам зателефонує наш менеджер!";
        emailSender.send(quickOrderFormDTO.getEmail(), "Магазин HP-Store", message);
        return ResponseEntity.ok(orderMain);
    }

    @GetMapping("/order")
    public Page<OrderMain> orderList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     Authentication authentication) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<OrderMain> orderPage;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            orderPage = orderService.findByBuyerEmail(authentication.getName(), request);
        } else {
            orderPage = orderService.findAll(request);
        }
        return orderPage;
    }

    @PatchMapping("/order/cancel/{id}")
    public ResponseEntity<OrderMain> cancel(@PathVariable("id") Long orderId, Authentication authentication) {
        OrderMain orderMain = orderService.findOne(orderId);
        if (!authentication.getName().equals(orderMain.getBuyerEmail()) && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(orderService.cancel(orderId));
    }

    @PatchMapping("/order/finish/{id}")
    public ResponseEntity<OrderMain> finish(@PathVariable("id") Long orderId, Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(orderService.finish(orderId));
    }

    @GetMapping("/order/{id}")
    public ResponseEntity show(@PathVariable("id") Long orderId, Authentication authentication) {
        boolean isCustomer = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        OrderMain orderMain = orderService.findOne(orderId);
        if (isCustomer && !authentication.getName().equals(orderMain.getBuyerEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(orderMain);
    }
}
