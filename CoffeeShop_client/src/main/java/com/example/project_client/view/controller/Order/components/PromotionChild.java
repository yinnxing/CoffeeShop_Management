package com.example.project_client.view.controller.Order.components;

import com.example.project_client.model.Promotion;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Getter
public class PromotionChild extends VBox {
    private final Label name;
    private final Label date;
    public PromotionChild(Promotion promotion){
        name = new Label(promotion.getName());
        date = new Label(promotion.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" - "+promotion.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        getChildren().addAll(name,date);
        setOnMouseClicked(e-> {
            try {
                click(promotion);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    private void click(Promotion promotion) throws IOException {
        Router.setData(Pages.MAIN_VIEW,promotion);
        Router.switchTo(Pages.PROMOTION_VIEW);
    }
}
