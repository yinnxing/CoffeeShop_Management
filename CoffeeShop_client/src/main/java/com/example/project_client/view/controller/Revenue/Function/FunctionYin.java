package com.example.project_client.view.controller.Revenue.Function;
import atlantafx.base.theme.Styles;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FunctionYin {
    private static final NumberFormat us = NumberFormat.getCurrencyInstance(Locale.US);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
    public static void validDatePicker(DatePicker datePicker){
        datePicker.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(LocalDate.now()));
                    }});
        datePicker.setValue(LocalDate.now());
    }

    public static <T> void validColumnMoney(TableColumn<T, Integer> tableColumn){
        tableColumn.setCellFactory(salaryCalIntegerTableColumn -> new TableCell<T, Integer>(){
            @Override
            public void updateItem(Integer salary, boolean empty) {
                super.updateItem(salary, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(FunctionYin.convertMoney(salary));
                }
            }
        });
    }

    public static void validNumberAxis(NumberAxis numberAxis){
        numberAxis.setAutoRanging(false);
        numberAxis.setMinorTickCount(1);
    }

    public static String convertDate(String date){
        String[] v = date.split("-");
        String result = v[v.length - 1];
        for(int i = v.length - 2; i >= 0; i--) {
            result += "-" + v[i];
        }
        return result;
    }

    public static void validCombobox(ComboBox<String> comboBox){
        comboBox.getItems().addAll("Theo ngày", "Theo tháng");
        comboBox.setValue("Theo ngày");
    }

    public static String convertDatePerMonth(LocalDate localDate){
        return localDate.format(formatter);
    }
    public static String convertMoney(Integer money){
        if(money > 0){
            String result = us.format(money);
            return result.substring(1, result.length() - 3) + " đồng";
        }
        if(money < 0){
            String result = us.format(-money);
            return "-" + result.substring(1, result.length() - 3) + " đồng";
        }
        return "0 đồng";
    }

    public static Button createBtn(String text, FontIcon fontIcon){
        Button button = new Button(text, fontIcon);
        button.getStyleClass().addAll(Styles.BUTTON_OUTLINED, Styles.ACCENT, Styles.LARGE);
        button.setPrefHeight(50);
        button.setPrefWidth(200);
        button.setMnemonicParsing(true);
        button.setContentDisplay(ContentDisplay.RIGHT);
        return button;
    }

    public static void validLabel(Label label){
        label.getStyleClass().addAll(Styles.ACCENT, Styles.TITLE_4);
    }

    public static void addBtnReturn(HBox hbox){
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        Button returnBtn = new Button(null, new FontIcon(Material2AL.CLOSE));
        returnBtn.setPrefSize(100, 100);

        returnBtn.getStyleClass().addAll(Styles.BUTTON_ICON,Styles.FLAT, Styles.DANGER);
        hbox.getChildren().addAll(region, returnBtn);
        returnBtn.setOnAction(e -> {
            try {
                Router.switchTo(Pages.ADMIN_VIEW);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    public static double calPc(Integer oldN, Integer newN){
        if(oldN == 0) return 0;
        double a = (newN - oldN) * 100.0 / oldN ;
        return Math.round(a * 100.0) / 100.0;
    }
}
