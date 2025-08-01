package kh.com.csx.repository;

import kh.com.csx.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    
    // Find all cart items for a specific customer
    List<Cart> findByCustomerCustomerId(Integer customerId);
    
    // Find a specific cart item by customer and menu
    Optional<Cart> findByCustomerCustomerIdAndMenuMenuId(Integer customerId, Integer menuId);
    
    // Count total items in customer's cart
    @Query("SELECT COUNT(c) FROM Cart c WHERE c.customer.customerId = :customerId")
    Long countCartItemsByCustomerId(@Param("customerId") Integer customerId);
    
    // Calculate total quantity in customer's cart
    @Query("SELECT COALESCE(SUM(c.quantity), 0) FROM Cart c WHERE c.customer.customerId = :customerId")
    Long getTotalQuantityByCustomerId(@Param("customerId") Integer customerId);
    
    // Delete all cart items for a customer (useful after placing order)
    void deleteByCustomerCustomerId(Integer customerId);
}
