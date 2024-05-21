package com.example.project_client.view.controller.Order.components;

import atlantafx.base.theme.Styles;
import com.example.project_client.event.Data;
import com.example.project_client.model.Product;
import com.example.project_client.view.controller.Order.interfaces.InitStyles;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import lombok.Getter;
import org.kordamp.ikonli.javafx.FontIcon;

import org.kordamp.ikonli.material2.Material2OutlinedAL;

@Getter
public class ProductInPromotion extends HBox implements InitStyles {
    private final TextField textField = new TextField();
    private final Button button = new Button(null, new FontIcon(Material2OutlinedAL.CLOSE));

    public ProductInPromotion(Product product, Double percent) {
        this.setAlignment(javafx.geometry.Pos.CENTER);
        this.setSpacing(10.0);

        Label labelCoffee = new Label(product.getName());

        Region region = new Region();
        HBox.setHgrow(region, javafx.scene.layout.Priority.ALWAYS);

        textField.setText(percent.toString());
        textField.setPrefHeight(26.0);
        textField.setPrefWidth(53.0);
        Label labelPercent = new Label("%");


        this.getChildren().addAll(labelCoffee, region, textField, labelPercent, button);

        this.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));

           setDisable(!(Data.getUser().getStaffId() ==null));

    }

    @Override
    public void initStyle() {
        button.getStyleClass().add(Styles.FLAT);
    }
}

