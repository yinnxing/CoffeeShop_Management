package com.example.project_client.view.controller.Customer;

import com.example.project_client.model.Customer;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReadCustomerView implements Initializable {

    @FXML
    private Label dob;

    @FXML
    private Label nameField;

    @FXML
    private Label phoneNumField;
    @FXML
    private Label totalField;
    public static Customer customer;

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer customer) {

        ReadCustomerView.customer = customer;
    }


    @FXML
    void cancel(ActionEvent event) throws IOException {
        Router.goTo(Pages.CUSTOMER_VIEW);
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        phoneNumField.setText(customer.getPhoneNumber());
        nameField.setText(customer.getName());
        dob.setText(customer.getDob());
        totalField.setText(String.valueOf(customer.getTotal()));


    }

}
