package micro.proyect.customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import micro.proyect.customer.repository.entity.Customer;
import micro.proyect.customer.repository.entity.Region;
import micro.proyect.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/customers")
public class CustomerREST {
    @Autowired
    private CustomerService customerService;

    // -------------------------------- All Customers --------------------------------
    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomers(@RequestParam(value = "regionId", required = false) Long regionId) {
        List<Customer> customers = new ArrayList<>();

        if (regionId == null) {
            customers = customerService.findCustomerAll();

            if (customers.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            Region region = new Region();
            region.setId(regionId);
            customers = customerService.findCustomersByRegion(region);

            if (customers == null) {
                log.error("Customers with region id {} not found.", regionId);
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(customers);
    }

    // -------------------------------- Single Customer --------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id) {
        log.info("Fetching Customer with id {}", id);
        Customer customer = customerService.getCustomer(id);

        if (customer == null) {
            log.error("Customer with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer);
    }

    // -------------------------------- Create Customer --------------------------------
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
        log.info("Creating Customer: {}", customer);

        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Customer customerInDB = customerService.createCustomer(customer);

        return ResponseEntity.status(HttpStatus.CREATED).body(customerInDB);
    }

    // -------------------------------- Update Customer --------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCostumer(@PathVariable("id") Long id, @RequestBody Customer customer) {
        log.info("Updating costumer with id {}", id);

        Customer currentCustomer = customerService.getCustomer(id);

        if (currentCustomer == null) {
            log.error("Unable to update. Customer with id {} not found", id);

            return ResponseEntity.notFound().build();
        }

        customer.setId(id);
        currentCustomer = customerService.updateCustomer(customer);

        return ResponseEntity.ok(currentCustomer);
    }

    // -------------------------------- Delete Customer --------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delteCustomer(@PathVariable("id") Long id) {
        log.info("Fetching and Deleting Customer with id {}", id);

        Customer customer = customerService.getCustomer(id);

        if (customer == null) {
            log.error("Unable to detele. Customer with id {} not found", id);

            return ResponseEntity.notFound().build();
        }

        customer = customerService.deleteCustomer(customer);

        return ResponseEntity.ok(customer);
    }

    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());

                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }
}
