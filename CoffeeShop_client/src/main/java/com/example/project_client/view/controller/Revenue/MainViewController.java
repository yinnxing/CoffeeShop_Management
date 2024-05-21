package com.example.project_client.view.controller.Revenue;
import atlantafx.base.theme.Styles;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import com.example.project_client.view.controller.Revenue.Function.FunctionYin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        VBox.setVgrow(revenueAndProfitHbox, Priority.ALWAYS);
        Button returnBtn = new Button(null, new FontIcon(Material2AL.CLOSE));
        returnBtn.getStyleClass().addAll(Styles.BUTTON_ICON,Styles.FLAT, Styles.DANGER);
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        returnHbox.getChildren().addAll(region, returnBtn);
        returnBtn.setOnAction(e -> {
            try {
                Router.switchTo(Pages.ADMIN_VIEW);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        Button openWdSalaryCalViewBtn = FunctionYin.createBtn("Tiền lương", new FontIcon(Material2AL.GROUP));
        Button openWdBillProductCalViewBtn = FunctionYin.createBtn("Doanh thu", new FontIcon(Material2AL.ADD_BUSINESS));
        salaryAndIngHbox.getChildren().addAll(openWdSalaryCalViewBtn, openWdBillProductCalViewBtn);

        openWdSalaryCalViewBtn.setOnAction(e -> {
            try {
                Router.switchTo(Pages.SALARY_CAL_VIEW);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        openWdBillProductCalViewBtn.setOnAction(e -> {
            try {
                Router.switchTo(Pages.BILL_PRODUCT_CAL_VIEW);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        salaryAndIngHbox.setSpacing(50);
        salaryAndIngHbox.setPadding(new Insets(100, 0, 0, 150));
        revenueAndProfitHbox.setSpacing(50);
        revenueAndProfitHbox.setPadding(new Insets(100, 0, 0, 150));
    }

    @FXML
    private HBox returnHbox;
    @FXML
    private HBox salaryAndIngHbox;
    @FXML
    private HBox revenueAndProfitHbox;

}
