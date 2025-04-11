package org.basic_crud.practice_1.customer;

import org.basic_crud.practice_1.exceptions.DuplicateResourceException;
import org.basic_crud.practice_1.exceptions.RequestValidationException;
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

    public void deleteCustomerById(Integer id){
        if(!customerDAO.existsCustomerWithId(id)){
            throw new ResourceNotFoundException(
                    "The customer with id [%s] not found.".formatted(id)
            );
        }
        customerDAO.deleteCustomerById(id);
    }

    public void updateCustomer(Integer id, CustomerUpdateRequest customerUpdateRequest){
        Customer target = getCustomerById(id);
        boolean changed = false;

        // Check for change of age
        if(customerUpdateRequest.age() != null && !customerUpdateRequest.age().equals(target.getAge())){
            target.setAge(customerUpdateRequest.age());
            changed = true;
        }

        // Check for change of name
        if(customerUpdateRequest.name() != null && !customerUpdateRequest.name().equals(target.getName())){
            target.setName(customerUpdateRequest.name());
            changed = true;
        }

        // Check for change of email
        if(customerUpdateRequest.email() != null && !customerUpdateRequest.email().equals(target.getEmail())){
            String newEmail = customerUpdateRequest.email();
            if(customerDAO.existsCustomerWithEmail(newEmail)){
                throw new DuplicateResourceException(
                        "The email is not unique."
                );
            }
            target.setEmail(newEmail);
            changed = true;
        }

        // If no changes were detected throw an exception
        if(!changed){
            throw new RequestValidationException(
                    "No data changes found."
            );
        }

        // If there were changes save them
        customerDAO.updateCustomer(target);
    }
}
