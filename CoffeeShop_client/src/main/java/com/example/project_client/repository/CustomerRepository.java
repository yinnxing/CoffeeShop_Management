package com.example.project_client.repository;

import com.example.project_client.data.Api;
import com.example.project_client.data.JsonUtils;
import com.example.project_client.data.Request;
import com.example.project_client.model.Customer;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CustomerRepository {
    public Boolean checkCustomer(String phone) throws IOException {
        return JsonUtils.fromJson(Request.sendGetRequest(Api.customerApi + "/check/" + (Objects.equals(phone, "") ? "t" : phone)), Boolean.class);
    }

    public Customer getCustomer(String phone) throws IOException {
        return JsonUtils.fromJson(Request.sendGetRequest(Api.customerApi + "/getByPhone?phoneNumber=" + phone), Customer.class);
    }

    public void saveCustomer(Customer customer) throws Exception {
        Request.sendPutRequest(Api.customerApi + "/save", Objects.requireNonNull(JsonUtils.toJson(customer)));
    }

    public List<Customer> getCustomersApi() throws IOException {
        return JsonUtils.fromJson(Request.sendGetRequest(Api.customerApi + "/getAll"), new TypeReference<List<Customer>>() {});
    }
    public void deleteCustomer(Long id) throws IOException {
        // Assuming there is an API endpoint for deleting a customer by phone number
        String apiUrl = Api.customerApi + "/delete/" + id;

        // Send a DELETE request to the server
        Request.sendDeleteRequest(apiUrl);
        try {
            // Send a DELETE request to the server
            Request.sendDeleteRequest(apiUrl);
        } catch (IOException e) {
            // Handle or log the exception
            System.err.println("Error deleting customer: " + e.getMessage());
            throw e; // Propagate the exception if needed
        }
    }

}
