package com.example.project_client.view.controller.Customer;

import com.example.project_client.HelloApplication;
import com.example.project_client.event.Data;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class AdminView {


    @FXML
    VBox mainPane;
    @FXML
    HBox analyze;
    @FXML
    HBox ingredient;
    @FXML
    HBox product;
    @FXML
    HBox staff;
    @FXML
    HBox userBtn;
    @FXML
    VBox vBox;
    @FXML
    void initialize() throws IOException {
        //Quyen
        if(Data.getUser().getStaffId()!=null){
            vBox.getChildren().removeAll(analyze,ingredient,product,staff,userBtn);
        }
        String scenePath = Router.getRouterLabel().get(Pages.MAIN_VIEW);
        Parent resource = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource(scenePath)));
        mainPane.getChildren().setAll(resource);
    }



    @FXML
    void HandleCustomerClicked() throws IOException {
        Parent customerView = Router.loadTo(Pages.CUSTOMER_VIEW);
        mainPane.getChildren().setAll(customerView);

    }
    @FXML
    void HandleProductClicked() throws IOException {
    //    Parent productView = Router.loadTo(Pages.PRODUCT_VIEW);
    //    mainPane.getChildren().setAll(productView);
        Router.goTo(Pages.PRODUCT_VIEW);
    }

    @FXML
    void HandleRevenueClicked() throws IOException {
        //Parent productView = Router.loadTo(Pages.MAIN_VIEW_PROFIT);
       // mainPane.getChildren().setAll(productView);
        Router.goTo(Pages.BILL_PRODUCT_CAL_VIEW);
    }

    @FXML
    void HandleStaffClicked() throws IOException {
        Router.switchTo(Pages.STAFF_VIEW);
    }


    @FXML
    void HandleSignOut() throws IOException {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Xác nhận đăng xuất");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Bạn có chắc chắn muốn đăng xuất?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            Router.switchTo(Pages.LOGIN_VIEW);
        } else {

        }
    }

    @FXML
    void switchToMainView() throws IOException {
        String scenePath = Router.getRouterLabel().get(Pages.MAIN_VIEW);
        Parent resource = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource(scenePath)));
        mainPane.getChildren().setAll(resource);
    }
}

