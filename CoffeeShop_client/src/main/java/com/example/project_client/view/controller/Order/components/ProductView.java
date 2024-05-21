package com.example.project_client.view.controller.Order.components;

import atlantafx.base.controls.Card;
import atlantafx.base.theme.Styles;
import com.example.project_client.model.Product;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;


@Getter
public class ProductView extends Card {
    private final Product product;

    public ProductView(Product product) {
        this.product = product;
        getStyleClass().add(Styles.INTERACTIVE);
        setPadding(new Insets(10));
        ImageView imageView = new ImageView();
        imageView.setFitHeight(150.0);
        imageView.setFitWidth(150.0);
        imageView.setPreserveRatio(true);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(product.getImage())));
        imageView.setImage(image);
        Label nameLabel = new Label(product.getName());
        Label priceLabel = new Label(NumberFormat.getNumberInstance(Locale.US).format(product.getPrice()) + " VND");
        Label availabilityLabel = new Label(product.getAvailable() ? "Còn hàng" : "Hết hàng");
        VBox vBox = new VBox(nameLabel, priceLabel, availabilityLabel);
        setHeader(imageView);
        setBody(vBox);
        this.setDisable(!product.getAvailable());

    }
}
