package com.example.project_server.service;

import com.example.project_server.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface CustomerService {

    Optional<Customer> getCustomerById(Long id);

    boolean isCustomerExist(String phoneNumber);
    Customer getCustomerByPhone(String phoneNumber);
    String saveCustomer(Customer customer);

    List<Customer> getAllCustomers();


    ////
    Customer updateCustomer(Long id, Customer updatedCustomer);

    void deleteCustomer(Long id);
}
