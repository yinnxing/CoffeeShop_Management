package com.example.project_client.viewModel.Order;

import com.example.project_client.model.Customer;
import com.example.project_client.repository.CustomerRepository;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class CustomerInformationInputViewModel {
    @Getter
    boolean existCustomer;
    @Setter
    @Getter
    private Customer customer;

    private final CustomerRepository customerRepository = new CustomerRepository();

    public void findCustomer() throws IOException {
        if (customerRepository.checkCustomer(customer.getPhoneNumber())) {
            existCustomer = true;
            customer = customerRepository.getCustomer(customer.getPhoneNumber());
        } else{
            existCustomer = false;
        }
    }
}
