package dev.evansdev.customer;

import dev.evansdev.exception.DuplicateResourceException;
import dev.evansdev.exception.RequestValidationException;
import dev.evansdev.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {
    public final CustomerDAO customerDAO;

    public CustomerService(@Qualifier("jpa") CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.selectAllCustomers();
    }

    public Customer getCustomerById(Integer id) {
        return customerDAO.selectCustomerById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "customer with id [%s] not found".formatted(id)
                        )
                );
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        String email = customerRegistrationRequest.email();
        if (customerDAO.existsPersonWithEmail(email)) {
            throw new DuplicateResourceException("email already taken");
        }

        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age()
        );

        customerDAO.insertCustomer(customer);
    }

    public void deleteCustomerById(Integer customerId) {
        if (!customerDAO.existsPersonWithId(customerId)) {
            throw new ResourceNotFoundException("customer with id [%s] not found".formatted(customerId));
        }

        customerDAO.deleteCustomerById(customerId);
    }

    public void updateCustomer(Integer customerId, CustomerUpdateRequest updateRequest) {
        Customer customer = getCustomerById(customerId);
        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(customer.getName())) {
            customer.setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())) {
            customer.setAge(updateRequest.age());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())) {

            if (customerDAO.existsPersonWithEmail(updateRequest.email())) {
                throw new DuplicateResourceException("email already taken");
            }

            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }

        customerDAO.updateCustomer(customer);
    }
}
