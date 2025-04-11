package org.basic_crud.practice_1.customer;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("{id}")
    public Customer getCustomerById(
            @PathVariable("id") Integer id
    ){
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public void addCustomer(
            @RequestBody CustomerRegistrationRequest customerRegistrationRequest
    ){
        customerService.addCustomer(customerRegistrationRequest);
    }

    @DeleteMapping("{id}")
    public void deleteCustomerById(
            @PathVariable("id")
            Integer id
    ){
        customerService.deleteCustomerById(id);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") Integer customerId,
            @RequestBody CustomerUpdateRequest customerUpdateRequest
    ){
        customerService.updateCustomer(customerId, customerUpdateRequest);
    }
}
