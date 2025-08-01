package kh.com.csx.repository;

import kh.com.csx.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartMenuRepository extends JpaRepository<OrderMenu, Long> {
}
