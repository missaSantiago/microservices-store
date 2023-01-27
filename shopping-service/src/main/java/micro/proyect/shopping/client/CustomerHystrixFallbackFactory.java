package micro.proyect.shopping.client;

import micro.proyect.shopping.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerHystrixFallbackFactory implements CustomerClient {
    @Override
    public ResponseEntity<Customer> getCustomer(Long id) {
        Customer customer = Customer.builder()
                .name("none")
                .lastName("none")
                .email("none")
                .photoUrl("none").build();

        return ResponseEntity.ok(customer);
    }
}
