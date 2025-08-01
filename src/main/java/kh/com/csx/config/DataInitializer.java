package kh.com.csx.config;

import kh.com.csx.entity.Customer;
import kh.com.csx.entity.Menu;
import kh.com.csx.repository.CustomerRepository;
import kh.com.csx.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final MenuRepository menuRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only initialize if database is empty
        if (menuRepository.count() == 0) {
            initializeMenus();
        }
        
        if (customerRepository.count() == 0) {
            initializeCustomers();
        }
    }

    private void initializeMenus() {
        log.info("Initializing sample menu items...");
        
        Menu menu1 = Menu.builder()
                .code("MENU001")
                .nameEn("Grilled Chicken")
                .nameKh("មាន់អាំង")
                .nameCn("烤鸡")
                .descriptionEn("Delicious grilled chicken with herbs")
                .descriptionKh("មាន់អាំងឆ្ងាញ់ជាមួយម្រេច")
                .descriptionCn("美味的香草烤鸡")
                .price(12.50)
                .priceKh(50000.0)
                .status(1)
                .isHot(true)
                .createdAt(new Date())
                .build();

        Menu menu2 = Menu.builder()
                .code("MENU002")
                .nameEn("Beef Stir Fry")
                .nameKh("សាច់គោឆា")
                .nameCn("炒牛肉")
                .descriptionEn("Tender beef stir-fried with vegetables")
                .descriptionKh("សាច់គោទន់ឆាជាមួយបន្លែ")
                .descriptionCn("嫩牛肉炒蔬菜")
                .price(15.00)
                .priceKh(60000.0)
                .status(1)
                .isHot(false)
                .createdAt(new Date())
                .build();

        Menu menu3 = Menu.builder()
                .code("MENU003")
                .nameEn("Fish Amok")
                .nameKh("អាម៉ុកត្រី")
                .nameCn("鱼阿莫克")
                .descriptionEn("Traditional Cambodian fish curry")
                .descriptionKh("ម្ហូបត្រីបុរាណខ្មែរ")
                .descriptionCn("传统柬埔寨鱼咖喱")
                .price(18.00)
                .priceKh(72000.0)
                .status(1)
                .isHot(true)
                .createdAt(new Date())
                .build();

        Menu menu4 = Menu.builder()
                .code("MENU004")
                .nameEn("Vegetable Spring Rolls")
                .nameKh("នំបញ្ចុកបន្លែ")
                .nameCn("蔬菜春卷")
                .descriptionEn("Fresh vegetable spring rolls")
                .descriptionKh("នំបញ្ចុកបន្លែស្រស់")
                .descriptionCn("新鲜蔬菜春卷")
                .price(8.00)
                .priceKh(32000.0)
                .status(1)
                .isHot(false)
                .createdAt(new Date())
                .build();

        menuRepository.save(menu1);
        menuRepository.save(menu2);
        menuRepository.save(menu3);
        menuRepository.save(menu4);
        
        log.info("Sample menu items created successfully!");
    }

    private void initializeCustomers() {
        log.info("Initializing sample customers...");
        
        Customer customer1 = Customer.builder()
                .name("John Doe")
                .phone("+855123456789")
                .address("Phnom Penh, Cambodia")
                .telegramUsername("johndoe")
                .telegram_id("123456789")
                .createdAt(new Date())
                .build();

        Customer customer2 = Customer.builder()
                .name("Jane Smith")
                .phone("+855987654321")
                .address("Siem Reap, Cambodia")
                .telegramUsername("janesmith")
                .telegram_id("987654321")
                .createdAt(new Date())
                .build();

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        
        log.info("Sample customers created successfully!");
    }
}
