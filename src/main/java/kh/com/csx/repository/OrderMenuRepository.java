package kh.com.csx.repository;

import kh.com.csx.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepository extends JpaRepository<Order, Long> {
    // Additional query methods can be defined here if needed
}
