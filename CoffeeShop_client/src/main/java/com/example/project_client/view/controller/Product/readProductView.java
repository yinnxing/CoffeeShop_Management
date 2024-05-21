package com.example.project_client.view.controller.Product;

import com.example.project_client.model.Product;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class readProductView {
    @FXML
    private Label id, name, price, available;
    @FXML
    private ImageView image;
    private Product product;
    @FXML
    private void initialize() throws Exception {
        product = productView.getProduct();
        System.out.println("Read Product: "+product.getName());
        getInformation();
    }
    @FXML
    private void cancel() throws Exception {
        Router.switchTo(Pages.PRODUCT_VIEW);
    }
    private void getInformation() {
        id.setText(product.getId().toString());
        name.setText(product.getName());
        price.setText(product.getPrice().toString());
        available.setText(product.getAvailable().toString());
        image.setFitHeight(150.0);
        image.setFitWidth(200.0);
        image.setPreserveRatio(true);
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(product.getImage())));
        image.setImage(img);

    }
}