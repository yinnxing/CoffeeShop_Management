package com.example.project_client.view.controller.Order;


import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import com.example.project_client.view.controller.Order.components.DobFormatter;
import com.example.project_client.viewModel.Order.CustomerInformationInputViewModel;
import com.example.project_client.viewModel.Order.CreateOrderBillViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;



public class CustomerInformationInputView {
    @FXML
    TextField phoneNumber;
    @FXML
    TextField customerName;
    @FXML
    DatePicker dob;
    @FXML
    Label warning;
    private final CustomerInformationInputViewModel customerInformationInputViewModel = new CustomerInformationInputViewModel();
    private CreateOrderBillViewModel createOrderBillViewModel;

    @FXML
    public void initialize() {
        warning.setVisible(false);
        createOrderBillViewModel = (CreateOrderBillViewModel) Router.getData(Pages.CREATE_ORDER_VIEW);

        customerInformationInputViewModel.setCustomer(createOrderBillViewModel.getCustomer());

        dob.valueProperty().addListener(((observableValue, localDate, t1) -> customerInformationInputViewModel.getCustomer().setDob(DobFormatter.toString(t1))));
        phoneNumber.textProperty().addListener((e, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                phoneNumber.setText(newVal.replaceAll("\\D", ""));
            }
            customerInformationInputViewModel.getCustomer().setPhoneNumber(phoneNumber.getText());
            try {
                customerInformationInputViewModel.findCustomer();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (customerInformationInputViewModel.isExistCustomer()) {
                customerName.setText(customerInformationInputViewModel.getCustomer().getName());
                dob.setValue(DobFormatter.toDate(customerInformationInputViewModel.getCustomer().getDob()));
            } else {
                customerName.setText("");
                dob.setValue(DobFormatter.toDate(""));
                customerInformationInputViewModel.getCustomer().setTotal(0);
            }

        });
        customerName.textProperty().addListener((obs, oldVal, newVal) -> customerInformationInputViewModel.getCustomer().setName(newVal));


        dob.setValue(DobFormatter.toDate(customerInformationInputViewModel.getCustomer().getDob()));
        phoneNumber.setText(customerInformationInputViewModel.getCustomer().getPhoneNumber());
        customerName.setText(customerInformationInputViewModel.getCustomer().getName());

    }


    @FXML
    public void confirm() throws Exception {

        if (customerInformationInputViewModel.getCustomer().getPhoneNumber().isEmpty()||customerInformationInputViewModel.getCustomer().getPhoneNumber().matches("(0[35789])+([0-9]{8})")){

            createOrderBillViewModel.setCustomer(customerInformationInputViewModel.getCustomer());
            createOrderBillViewModel.checkCustomerForPromo(dob.getValue());
            createOrderBillViewModel.setCustomerPhone();

            Router.closeDialog();
        }else {
            if(!warning.isVisible()) warning.setVisible(true);
        }

    }

}
