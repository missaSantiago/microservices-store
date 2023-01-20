package micro.proyect.customer.service;

import lombok.extern.slf4j.Slf4j;
import micro.proyect.customer.repository.CustomerRepository;
import micro.proyect.customer.repository.entity.Customer;
import micro.proyect.customer.repository.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> findCustomerAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomersByRegion(Region region) {
        return customerRepository.findByRegion(region);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer customerInDB = customerRepository.findByNumberID(customer.getNumberID());

        if (customerInDB != null) {
            return customerInDB;
        }

        customer.setState("CREATED");
        customerInDB = customerRepository.save(customer);

        return customerInDB;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer customerInDB = getCustomer(customer.getId());
        if (customerInDB == null) {
            return null;
        }

        customerInDB.setName(customer.getName());
        customerInDB.setLastName(customer.getLastName());
        customerInDB.setEmail(customer.getEmail());
        customerInDB.setPhotoUrl(customer.getPhotoUrl());

        return customerRepository.save(customerInDB);
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        Customer customerInDB = getCustomer(customer.getId());

        if (customerInDB == null) {
            return null;
        }

        customer.setState("DELETED");

        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}
