package com.example.project_client;

import atlantafx.base.theme.*;
import com.example.project_client.router.Pages;

import com.example.project_client.router.Router;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        Router.bind(this, stage, "Coffee Management", 1366, 700);
        init();
        Router.goTo(Pages.LOGIN_VIEW);
    }
    public void init(){
        //
        Router.setRouter(Pages.LOGIN_VIEW, "Customer/login-view.fxml");
        Router.setRouter(Pages.ADMIN_VIEW, "Customer/admin-view.fxml");
        Router.setRouter(Pages.CUSTOMER_VIEW, "Customer/customer-view.fxml");
        Router.setRouter(Pages.ADD_CUSTOMER_VIEW, "Customer/add-customer-view.fxml");
        Router.setRouter(Pages.UPDATE_CUSTOMER_VIEW, "Customer/update-customer-view.fxml");
        Router.setRouter(Pages.READ_CUSTOMER_VIEW, "Customer/read-customer-view.fxml");
        //
        Router.setRouter(Pages.MAIN_VIEW, "Order/main-view.fxml");
        Router.setRouter(Pages.CREATE_ORDER_VIEW, "Order/create-order-view.fxml");
        Router.setRouter(Pages.ORDER_BILL_VIEW, "Order/order-bill-view.fxml");
        Router.setRouter(Pages.PROMOTION_VIEW, "Order/promotion-view.fxml");
        Router.setRouter(Pages.CUSTOMER_INFORMATION_INPUT_VIEW, "Order/customer-information-input-view.fxml");
        //
        Router.setRouter(Pages.MAIN_VIEW_PROFIT, "Revenue/main-view.fxml");
        Router.setRouter(Pages.SALARY_CAL_VIEW, "Revenue/SalaryCalView.fxml");
        Router.setRouter(Pages.BILL_INGREDIENT_CAL_VIEW, "Revenue/BillIngredientCalView.fxml");
        Router.setRouter(Pages.BILL_PRODUCT_CAL_VIEW, "Revenue/BillProductCalView.fxml");
        Router.setRouter(Pages.PROFIT_CAL_VIEW, "Revenue/ProfitCalView.fxml");
        Router.setRouter(Pages.CREATE_USER_VIEW, "Revenue/createUserView.fxml");
        Router.setRouter(Pages.UPDATE_USER_VIEW, "Revenue/updateUserView.fxml");

        Router.setRouter(Pages.PRODUCT_VIEW, "Product/productScene1.fxml");
        Router.setRouter(Pages.ADD_PRODUCT, "Product/addProduct1.fxml");
        Router.setRouter(Pages.CHANGE_PRODUCT, "Product/changeProduct.fxml");
        Router.setRouter(Pages.READ_PRODUCT,"Product/readProduct.fxml");


    }
    public static void main(String[] args) {
        launch();
    }
}