package andry.hais.shopapi.api;


import andry.hais.shopapi.dto.QuantityDTO;
import andry.hais.shopapi.entity.*;
import andry.hais.shopapi.form.ItemForm;
import andry.hais.shopapi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;


@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductInOrderService productInOrderService;
    @Autowired
    OrderService orderService;

    @PostMapping("")
    public ResponseEntity<Cart> mergeCart(@RequestBody Collection<ProductInOrder> productInOrders, Principal principal) {
        User user = userService.findOne(principal.getName());
        try {
            cartService.mergeLocalCart(productInOrders, user);
        } catch (Exception e) {
            ResponseEntity.badRequest().body("Merge Cart Failed");
        }
        return ResponseEntity.ok(cartService.getCart(user));
    }

    @GetMapping("")
    public Cart getCart(Principal principal) {
        User user = userService.findOne(principal.getName());
        return cartService.getCart(user);
    }

    @PostMapping("/add")
    public boolean addToCart(@RequestBody ItemForm itemForm, Principal principal) {
        ProductInfo productInfo = productService.findOne(itemForm.getProductId());
        try {
            mergeCart(Collections.singleton(new ProductInOrder(productInfo, itemForm.getProductSize(), itemForm.getQuantity())), principal);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PutMapping("/{itemId}")
    public ProductInOrder modifyItem(@PathVariable("itemId") String itemId, @RequestBody QuantityDTO quantityDTO, Principal principal) {
        User user = userService.findOne(principal.getName());
        productInOrderService.update(itemId, quantityDTO.getQuantity(), user);
        return productInOrderService.findOne(itemId, user);
    }

    @DeleteMapping("/{itemId}")
    public boolean deleteItem(@PathVariable("itemId") String itemId, Principal principal) {
        User user = userService.findOne(principal.getName());
        try {
            cartService.delete(itemId, user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderMain> checkout(Principal principal) {
        User user = userService.findOne(principal.getName());// Email as username
        return ResponseEntity.ok(cartService.checkout(user));
    }
}
