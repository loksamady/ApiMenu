package kh.com.csx.service;

import jakarta.transaction.Transactional;
import kh.com.csx.entity.Cart;
import kh.com.csx.entity.Customer;
import kh.com.csx.entity.Menu;
import kh.com.csx.entity.Order;
import kh.com.csx.repository.CartRepository;
import kh.com.csx.repository.CustomerRepository;
import kh.com.csx.repository.MenuRepository;
import kh.com.csx.repository.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusinessService {
    private final CustomerRepository customRepo;
    private final MenuRepository menuRepo;
    private final CartRepository cartRepo;
    private final OrderMenuRepository orderMenuRepo;

    public Customer saveCustomer(Customer c) {
        if (c == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        return customRepo.save(c);
    }

    public List<Customer> getAllCustomers() {
        return customRepo.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
    }

    public Menu saveMenu(Menu m) {
        if (m == null) {
            throw new IllegalArgumentException("Menu cannot be null");
        }
        return menuRepo.save(m);
    }

    public List<Menu> getAllMenus() {
        return menuRepo.findAll();
    }

    public Menu getMenuById(Integer id) {
        return menuRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with ID: " + id));
    }

    @Transactional
    public Cart addToCart(Long customerId, Integer menuId, Integer quantity) {
        Customer c = customRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
        Menu m = menuRepo.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found with ID: " + menuId));

        // Check if item already exists in cart
        var existingCartItem = cartRepo.findByCustomerCustomerIdAndMenuMenuId(
                customerId.intValue(), menuId);

        if (existingCartItem.isPresent()) {
            // Update quantity if item already in cart
            Cart cart = existingCartItem.get();
            cart.setQuantity(cart.getQuantity() + (quantity != null ? quantity : 1));
            return cartRepo.save(cart);
        } else {
            // Add new item to cart
            return cartRepo.save(Cart.builder()
                    .customer(c)
                    .menu(m)
                    .quantity(quantity != null ? quantity : 1)
                    .build());
        }
    }

    public Order placeOrder(Long customerId, Long cartMenuId, Long qty) {
        if (qty == null || qty <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        Customer c = customRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
        Cart cart = cartRepo.findById(cartMenuId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with ID: " + cartMenuId));
        Order order = Order.builder()
                .customer(c).customer(cart.getCustomer()).quantity(qty)
                .orderedAt(LocalDateTime.now())
                .build();
        return orderMenuRepo.save(order);
    }

    public List<Order> listCustomerOrders(Long customerId) {
        // TODO: Consider adding a repository method like findByCustomerId for better performance
        // For now, using stream filtering but this should be optimized for production
        return orderMenuRepo.findAll().stream()
                .filter(order -> order.getCustomer().getCustomerId().equals(customerId.intValue()))
                .collect(Collectors.toList());
    }

    public void deleteOrderMenu(Long orderMenuId) {
        orderMenuRepo.deleteById(orderMenuId);
    }

    // New cart-specific methods
    public List<Cart> getCartItems(Long customerId) {
        return cartRepo.findByCustomerCustomerId(customerId.intValue());
    }

    public Long getCartItemCount(Long customerId) {
        return cartRepo.countCartItemsByCustomerId(customerId.intValue());
    }

    public Long getCartTotalQuantity(Long customerId) {
        return cartRepo.getTotalQuantityByCustomerId(customerId.intValue());
    }

    @Transactional
    public void clearCart(Long customerId) {
        cartRepo.deleteByCustomerCustomerId(customerId.intValue());
    }

    @Transactional
    public Cart updateCartItemQuantity(Long cartItemId, Integer newQuantity) {
        if (newQuantity == null || newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        Cart cart = cartRepo.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with ID: " + cartItemId));
        cart.setQuantity(newQuantity);
        return cartRepo.save(cart);
    }

    @Transactional
    public void removeCartItem(Long cartItemId) {
        cartRepo.deleteById(cartItemId);
    }
}
