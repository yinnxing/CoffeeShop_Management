package com.example.project_server.controller;

import com.example.project_server.entity.Customer;
import com.example.project_server.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getByPhone")
    public ResponseEntity<Customer> getCustomerByPhone(@RequestParam String phoneNumber) {
        Customer customer = customerService.getCustomerByPhone(phoneNumber);

        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            // Handle the case when the customer is not found
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/check/{phoneNumber}")
    public Boolean checkCustomer(@PathVariable String phoneNumber) {
        return customerService.isCustomerExist(phoneNumber);
    }

    @PostMapping("/save")
    public String saveCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    ////
    @GetMapping("/getAll")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }


    @PutMapping("/update/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}