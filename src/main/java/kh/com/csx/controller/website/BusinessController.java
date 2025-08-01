package kh.com.csx.controller.website;

import kh.com.csx.constant.Constant;
import kh.com.csx.entity.Cart;
import kh.com.csx.entity.Customer;
import kh.com.csx.entity.Menu;
import kh.com.csx.entity.Order;
import kh.com.csx.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(Constant.API_V1_WEBSITE)
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService svc;

    // Customer CRUD
    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer c) {
        return svc.saveCustomer(c);
    }

//    @GetMapping("/customers")
//    public List<Customer> getAllCustomers() {
//        return svc.getAllCustomers();
//    }

//    @GetMapping("/customers/{id}")
//    public Customer getCustomerById(@PathVariable Long id) {
//        return svc.getCustomerById(id);
//    }

    // Menu CRUD
    @PostMapping("/menus")
    public Menu createMenu(@RequestBody Menu m) {
        return svc.saveMenu(m);
    }

    @GetMapping("/menus")
    public List<Menu> getAllMenus() {
        return svc.getAllMenus();
    }

    @GetMapping("/menus/{id}")
    public Menu getMenuById(@PathVariable Integer id) {
        return svc.getMenuById(id);
    }

    // Cart Operations
    @PostMapping("/customers/{cid}/cart")
    public Cart addToCart(@PathVariable Long cid, @RequestBody Map<String, Integer> body) {
        return svc.addToCart(cid, body.get("menuId"), body.get("quantity"));
    }

    @GetMapping("/customers/{cid}/cart")
    public List<Cart> getCartItems(@PathVariable Long cid) {
        return svc.getCartItems(cid);
    }

    @PutMapping("/cart/{cartId}")
    public Cart updateCartQuantity(@PathVariable Long cartId, @RequestBody Map<String, Integer> body) {
        return svc.updateCartItemQuantity(cartId, body.get("quantity"));
    }

    @DeleteMapping("/cart/{cartId}")
    public void removeCartItem(@PathVariable Long cartId) {
        svc.removeCartItem(cartId);
    }

    @DeleteMapping("/customers/{cid}/cart")
    public void clearCart(@PathVariable Long cid) {
        svc.clearCart(cid);
    }

    @GetMapping("/customers/{cid}/cart/count")
    public Map<String, Object> getCartSummary(@PathVariable Long cid) {
        return Map.of(
                "itemCount", svc.getCartItemCount(cid),
                "totalQuantity", svc.getCartTotalQuantity(cid)
        );
    }

    @GetMapping("/customers/{cid}/cart-menu")
    public List<Cart> getCartMenu(@PathVariable Long cid) {
        return svc.getCartItems(cid);
    }

    @PostMapping("/customers/{cid}/cart-menu")
    public Cart addToCartMenu(@PathVariable Long cid, @RequestBody Map<String, Integer> body) {
        return svc.addToCart(cid, body.get("menuId"), body.get("quantity"));
    }

    // Order Operations
    @PostMapping("/customers/{cid}/orders")
    public Order placeOrder(@PathVariable Long cid, @RequestBody Map<String, Long> body) {
        return svc.placeOrder(cid, body.get("cartMenuId"), body.get("quantity"));
    }

    @GetMapping("/customers/{cid}/orders")
    public List<Order> listOrders(@PathVariable Long cid) {
        return svc.listCustomerOrders(cid);
    }

    @GetMapping("/customers/{cid}/order-menu")
    public List<Order> getOrderMenu(@PathVariable Long cid) {
        return svc.listCustomerOrders(cid);
    }

    @PostMapping("/customers/{cid}/order-menu")
    public Order placeOrderMenu(@PathVariable Long cid, @RequestBody Map<String, Long> body) {
        return svc.placeOrder(cid, body.get("cartMenuId"), body.get("quantity"));
    }

    @DeleteMapping("/orders/{id}")
    public void deleteOrder(@PathVariable Long id) {
        svc.deleteOrderMenu(id);
    }
}
