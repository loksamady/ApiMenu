package kh.com.csx.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_menu_id")
    private Long cartMenuId;

    @Column(name = "quantity", nullable = false)
    @Builder.Default
    private Integer quantity = 1; // Default quantity is 1

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Menu menu;

    @PrePersist
    protected void onCreate() {
        if (addedAt == null) {
            addedAt = LocalDateTime.now();
        }
        if (quantity == null || quantity <= 0) {
            quantity = 1;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (quantity == null || quantity <= 0) {
            quantity = 1;
        }
    }
}