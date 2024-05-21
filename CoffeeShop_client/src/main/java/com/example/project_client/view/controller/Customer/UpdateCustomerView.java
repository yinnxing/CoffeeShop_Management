package com.example.project_client.view.controller.Customer;

import com.example.project_client.model.Customer;
import com.example.project_client.repository.CustomerRepository;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import com.example.project_client.view.controller.Order.components.DobFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateCustomerView implements Initializable {
    public static Customer selectCustomer;
    public static Customer getSelectCustomer() {
        return selectCustomer;
    }
    public static void setSelectCustomer(Customer selectCustomer) {
        UpdateCustomerView.selectCustomer = selectCustomer;
    }
    public static CustomerView customerViewUpdate;
    public static void setCustomerViewUpdate(CustomerView customerViewUpdate){
        UpdateCustomerView.customerViewUpdate = customerViewUpdate;
    }

    @FXML
    private DatePicker dob;

    @FXML
    private TextField nameField;

    @FXML
    private Label phoneNumField;

    @FXML
    private Button resetCustomerBtn;

    @FXML
    private Button saveCustomerBtn;
    @FXML
    private Label totalField;


    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }


    private CustomerRepository customerRepository = new CustomerRepository();

    private String getPhoneNumber() {
        return phoneNumField.getText();
    }

    private String getName() {
        return nameField.getText();
    }


    @FXML
    private void saveCustomer(ActionEvent event) throws IOException {
        String phoneNumber = selectCustomer.getPhoneNumber();
        String name = getName();
        LocalDate selectedDate = dob.getValue();
        String formattedDob = DobFormatter.toString(selectedDate);

        if (validateName(name) && emptyValidation("dob", dob.getEditor().getText().isEmpty())) {
                    Customer existingCustomer = customerRepository.getCustomer(phoneNumber);
                    existingCustomer.setName(name);
                    existingCustomer.setDob(formattedDob);
                    existingCustomer.setTotal(existingCustomer.getTotal());
                    try {
                        customerRepository.saveCustomer(existingCustomer);
                        customerViewUpdate = (CustomerView) Router.getData(Pages.CUSTOMER_VIEW);
                        customerViewUpdate.loadCustomerData();
                        saveAlert(existingCustomer);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
        }





    private void clearFields() {
        nameField.clear();
        dob.getEditor().clear();
    }

    private void saveAlert(Customer customer) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Customer updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The customer " + customer.getPhoneNumber() + " " + customer.getName() + " has been updated.");
        alert.showAndWait();
    }


    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if (empty) {
            alert.setContentText("Please Enter " + field);
        }
        else {
            alert.setContentText("Please Enter Valid " + field);
        }

        alert.showAndWait();

    }

    public boolean validateName(String name) {
        if (name == null || name.trim().length() == 0 || name.equals("null") || !name.matches("^[a-zA-Zà-ỹẠ-ỴđĐ]+[\\-'\\s]?[a-zA-Zà-ỹẠ-ỴđĐ ]+$")) {
            validationAlert("Name", false);
            return false;
        }
        return true;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        phoneNumField.setText(selectCustomer.getPhoneNumber());
        nameField.setText(selectCustomer.getName());
        System.out.println(selectCustomer.getDob());
        LocalDate localDate = DobFormatter.toDate(selectCustomer.getDob());
        dob.setValue(localDate);
        totalField.setText(String.valueOf(selectCustomer.getTotal()));

    }
}

