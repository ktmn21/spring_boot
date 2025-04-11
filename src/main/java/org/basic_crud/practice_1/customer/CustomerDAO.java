package org.basic_crud.practice_1.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(Integer id);
    void insertCustomer(Customer customer);
    boolean existsCustomerWithEmail(String email);
    boolean existsCustomerWithId(Integer id);
    void deleteCustomerById(Integer id);
    void updateCustomer(Customer customer);
}
