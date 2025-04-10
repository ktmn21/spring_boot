package org.basic_crud.practice_1.customer;

import org.basic_crud.practice_1.exceptions.DuplicateResourceException;
import org.basic_crud.practice_1.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<Customer> getAllCustomers(){
        return customerDAO.getAllCustomers();
    }

    public Customer getCustomerById(Integer id){
        return customerDAO.getCustomerById(id)
                .orElseThrow( () -> new ResourceNotFoundException("The customer with id [%s] not found".formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        if(customerDAO.existsCustomerWithEmail(customerRegistrationRequest.email())){
            throw new DuplicateResourceException(
                    "Email is not unique"
            );
        }

        customerDAO.insertCustomer(
                new Customer(
                        customerRegistrationRequest.name(),
                        customerRegistrationRequest.email(),
                        customerRegistrationRequest.age()
                )
        );
    }
}
