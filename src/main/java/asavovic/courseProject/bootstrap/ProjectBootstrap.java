package asavovic.courseProject.bootstrap;

import asavovic.courseProject.entities.Customer;
import asavovic.courseProject.entities.Product;
import asavovic.courseProject.repositories.CustomerRepository;
import asavovic.courseProject.repositories.ProductRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
public class ProjectBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public ProjectBootstrap(CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        addUsers();
        addProducts();


    }

    public void addUsers() {

        Customer customer1 = new Customer();
        customer1.setEmail("123");
        customer1.setPassword("$2a$12$UkGgUpKf46BGqMhH87udtOgQKtW1DKFnnGTcvBhA3MixQmdxdSK3O");
        Customer customer2 = new Customer();
        customer2.setEmail("sel");
        customer2.setPassword("$2a$12$UkGgUpKf46BGqMhH87udtOgQKtW1DKFnnGTcvBhA3MixQmdxdSK3O");

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        /*Session session = new Session();
        session.setCustomer(customer2);
        sessionRepository.save(session);
        Cart cart = session.getCart();
        CartProductId cartProductId = new CartProductId();
        cartProductId.setCartId(cart.getId());
        cartProductId.setProductId(product1.getId());
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCartProductId(cartProductId);
        cartProduct.setCart(cart);
        cartProduct.setProduct(product1);
        cartProduct.setQuantity(10L);
        cartProductRepository.save(cartProduct);

        cart.setProducts(Collections.singleton(cartProduct));
        cartRepository.save(cart);

        product1.setCarts(Collections.singleton(cartProduct));
        productRepository.save(product1);

        Order order = new Order();
        order.setCart(cart);
        orderRepository.save(order);*/


    }

    public void addProducts() {
        Product product1 = new Product();
        product1.setName("Milk");
        product1.setQuantity(25L);
        product1.setPrice(200);

        Product product2 = new Product();
        product2.setName("Chocolate");
        product2.setQuantity(100L);
        product2.setPrice(110);

        Product product3 = new Product();
        product3.setName("Eggs");
        product3.setQuantity(1000L);
        product3.setPrice(25);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);


    }

}
