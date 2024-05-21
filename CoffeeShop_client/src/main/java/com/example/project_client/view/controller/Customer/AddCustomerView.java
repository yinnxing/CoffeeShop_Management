package com.example.project_client.view.controller.Customer;
import com.example.project_client.model.Customer;
import com.example.project_client.repository.CustomerRepository;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import com.example.project_client.view.controller.Order.components.DobFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class AddCustomerView {


    @FXML
    private DatePicker dob;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneNumField;

    @FXML
    private Button resetCustomerBtn;

    @FXML
    private Button saveCustomerBtn;

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }
    @FXML
    void cancel(ActionEvent event) throws IOException {
        Router.goTo(Pages.CUSTOMER_VIEW);
    }

    private CustomerRepository customerRepository = new CustomerRepository();

    private String getPhoneNumber() {
        return phoneNumField.getText();
    }

    private String getName() {
        return nameField.getText();
    }

    public static CustomerView customerViewAdd;
    public static void setCustomerView(CustomerView customerViewAdd){
        AddCustomerView.customerViewAdd = customerViewAdd;
    }
    @FXML
    private void saveCustomer(ActionEvent event) throws Exception {
        String phoneNumber = getPhoneNumber();
        String name = getName();
        if (validatePhoneNumber(phoneNumber) && validateName(name) && emptyValidation("dob", dob.getEditor().getText().isEmpty())) {
            Customer customer = new Customer();
            customer.setPhoneNumber(phoneNumber);
            customer.setName(name);
            customer.setTotal(0);
            LocalDate selectedDate = dob.getValue();
            String formattedDob = DobFormatter.toString(selectedDate);
            customer.setDob(formattedDob);

            // Check if the customer already exists
            if (customerRepository.checkCustomer(phoneNumber)) {
                boolean confirmUpdate = showConfirmationDialog("Customer already exists", "Do you want to update the existing customer?");
                if (confirmUpdate) {
                    Customer existingCustomer = customerRepository.getCustomer(phoneNumber);
                    existingCustomer.setName(customer.getName());
                    existingCustomer.setDob(customer.getDob());
                    customer.setTotal(existingCustomer.getTotal());
                    customerRepository.saveCustomer(existingCustomer);
                    customerViewAdd = (CustomerView) Router.getData(Pages.CUSTOMER_VIEW);
                    customerViewAdd.loadCustomerData();
                    saveAlert(customer);
                    clearFields();
                }
            } else {
                customerRepository.saveCustomer(customer);
                customerViewAdd = (CustomerView) Router.getData(Pages.CUSTOMER_VIEW);
                customerViewAdd.loadCustomerData();
                saveAlert(customer);
                clearFields();

            }


        }
    }





    private void clearFields() {
        phoneNumField.clear();
        nameField.clear();
        dob.getEditor().clear();
    }

    private void saveAlert(Customer customer) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Customer saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The customer " + customer.getPhoneNumber() + " " + customer.getName() + " has been created.");
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

    boolean validatePhoneNumber(String phoneNumber) {
        // verify if phone has 10 digits and start with 0
        if (phoneNumber.length() != 10 || phoneNumber.charAt(0) != '0') {
            validationAlert("Phone Number", false);

            return false;
        }
        // verify if phone contains only number
        try {
            Integer.parseInt(phoneNumber);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean showConfirmationDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}





