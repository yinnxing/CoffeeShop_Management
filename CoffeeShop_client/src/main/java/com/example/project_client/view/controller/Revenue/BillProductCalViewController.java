package com.example.project_client.view.controller.Revenue;

import atlantafx.base.theme.Styles;
import com.example.project_client.model.NameAndCount;
import com.example.project_client.model.TimeRequest;
import com.example.project_client.view.controller.Revenue.Function.FunctionYin;
import com.example.project_client.viewModel.Revenue.BillProductViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.chart.XYChart;

public class BillProductCalViewController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        billProductBarChart.setAnimated(false);
        FunctionYin.validNumberAxis(yAxistBillProduct);
        billProductLineChart.setAnimated(false);
        FunctionYin.validNumberAxis(yAxistBillProductLine);
        FunctionYin.validDatePicker(datePickStart);
        FunctionYin.validDatePicker(datePickEnd);
        FunctionYin.validCombobox(typeCbb);
        FunctionYin.addBtnReturn(returnHbox);
        Button salaryCalBtn = new Button("Tính");
        salaryCalBtn.getStyleClass().addAll(Styles.BUTTON_OUTLINED, Styles.SUCCESS, Styles.LARGE);
        salaryCalBtn.setPrefWidth(100);
        salaryCalBtn.setOnAction(e -> {
            try {
                billProductCal();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        inputHbox.getChildren().add(salaryCalBtn);
        initStyles();
    }
    @FXML
    private HBox returnHbox;
    @FXML
    private HBox inputHbox;
    @FXML
    private Label dateStartLabel;
    @FXML
    private DatePicker datePickStart;
    @FXML
    private Label dateEndLabel;
    @FXML
    private DatePicker datePickEnd;
    @FXML
    private Label typeCbbLabel;
    @FXML
    private ComboBox<String> typeCbb;
    @FXML
    private VBox parentTotalVbox;
    @FXML
    private Label sumBillProLabel;
    @FXML
    private Label totalBillProLabel;
    @FXML
    private LineChart<String, Number> billProductLineChart;
    @FXML
    private CategoryAxis xAxistBillProductLine;
    @FXML
    private NumberAxis yAxistBillProductLine;
    @FXML
    private BarChart<String, Number> billProductBarChart;
    @FXML
    private NumberAxis yAxistBillProduct;
    @FXML
    private VBox inforVbox;
    @FXML
    private Label dateInforLabel;
    @FXML
    private Label revenueInforLabel;
    @FXML
    private Button swapChartBtn;

    private final BillProductViewModel billProductViewModel = new BillProductViewModel();
    private void settingOutTime(int totalProfit){
        sumBillProLabel.setText("Không thể thống kê!");
        totalBillProLabel.setText("Tổng: " + FunctionYin.convertMoney(totalProfit));
        yAxistBillProductLine.setUpperBound(500000);
        yAxistBillProductLine.setTickUnit(50000);
        billProductLineChart.getData().clear();
    }
    private void popAlert(int type){
        parentTotalVbox.setVisible(false);
        swapChartBtn.setVisible(false);
        billProductBarChart.setVisible(false);
        billProductLineChart.setVisible(false);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(type == 1){
            alert.setContentText("Ngày bắt đầu không được lớn hơn ngày kết thúc!");
        }else{
            alert.setContentText("Đã có lỗi xảy ra với máy chủ, vui lòng thử lại sau!");
        }
        alert.showAndWait();
    }
    @FXML
    public void swapChart(){
        if(swapChartBtn.getText().equals("Xem thống kê")) {
            billProductLineChart.setVisible(false);
            billProductBarChart.setVisible(true);
            swapChartBtn.setText("Xem doanh thu");
        }else {
            billProductBarChart.setVisible(false);
            billProductLineChart.setVisible(true);
            swapChartBtn.setText("Xem thống kê");
        }
    }
    private void initStyles(){
        FunctionYin.validLabel(dateStartLabel);
        FunctionYin.validLabel(dateEndLabel);
        FunctionYin.validLabel(sumBillProLabel);
        FunctionYin.validLabel(totalBillProLabel);
        FunctionYin.validLabel(typeCbbLabel);
        FunctionYin.validLabel(dateInforLabel);
        FunctionYin.validLabel(revenueInforLabel);
        typeCbb.getStyleClass().add(Styles.TITLE_4);
        datePickStart.getStyleClass().add(Styles.TITLE_4);
        datePickEnd.getStyleClass().add(Styles.TITLE_4);
        swapChartBtn.getStyleClass().addAll(Styles.BUTTON_OUTLINED, Styles.SUCCESS);
    }
    @SuppressWarnings("unchecked")
    public void billProductCal() {
        if(datePickStart.getValue().isAfter(datePickEnd.getValue())){
            popAlert(1);
            return;
        }
        TimeRequest timeRequest = new TimeRequest(datePickStart.getValue(), datePickEnd.getValue());
        if(typeCbb.getValue().equals("Theo ngày")) {
            xAxistBillProductLine.setLabel("Ngày");
            try {
                handlePerDay(timeRequest);
            } catch (Exception e) {
                popAlert(0);
                return;
            }
        }else{
            xAxistBillProductLine.setLabel("Tháng");
            try {
                handlePerMonth(timeRequest);
            } catch (Exception e) {
                popAlert(0);
                return;
            }
        }
        try {
            int max = -1;
            XYChart.Series<String, Number> series= new  XYChart.Series<>();
            series.setName("Biểu đồ số lượng sản phẩm bán được kể từ: " + FunctionYin.convertDate(datePickStart.getValue().toString()) + " tới ngày " + FunctionYin.convertDate(datePickStart.getValue().toString()));
            List<NameAndCount> nameAndCounts = billProductViewModel.getCount(new TimeRequest(datePickStart.getValue(), datePickEnd.getValue()));
            for(NameAndCount n : nameAndCounts) {
                series.getData().add(new  XYChart.Data<>(FunctionYin.convertDate(n.getName()), n.getCount()));
                if(max < n.getCount()) max = n.getCount();
            }
            if(max > 0) {
                yAxistBillProduct.setUpperBound(max);
                if(max + 1 < 10) {
                    yAxistBillProduct.setTickUnit(1);
                }else {
                    max /= 10;
                    yAxistBillProduct.setTickUnit(max);
                }
            }else {
                yAxistBillProduct.setUpperBound(50);
                yAxistBillProduct.setTickUnit(5);
            }
            billProductBarChart.setData(FXCollections.observableArrayList(series));
        } catch (Exception e) {
            popAlert(0);
            return;
        }
        parentTotalVbox.setVisible(true);
        swapChartBtn.setVisible(true);
        billProductBarChart.setVisible(false);
        billProductLineChart.setVisible(true);
        swapChartBtn.setText("Xem thống kê");
    }

    @SuppressWarnings("unchecked")
    private void handlePerDay(TimeRequest timeRequest) throws Exception{
        List<NameAndCount> nameAndCounts = billProductViewModel.getPerDay(timeRequest);
        int totalProfit = 0;
        for(NameAndCount n : nameAndCounts){
            totalProfit += n.getCount();
        }if(datePickStart.getValue().until(datePickEnd.getValue(), ChronoUnit.DAYS) >= 40) {
            settingOutTime(totalProfit);
        }else{
            sumBillProLabel.setText("Tổng doanh thu:");
            totalBillProLabel.setText(FunctionYin.convertMoney(totalProfit));
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            billProductLineChart.setData(FXCollections.observableArrayList(series));
            LocalDate st = datePickStart.getValue(), en = datePickEnd.getValue();
            int max = -1, i, j = 0, sz = (int) st.until(en, ChronoUnit.DAYS);
            series.setName("Thống kê doanh thu từ ngày " + FunctionYin.convertDate(st.toString()) + " tới ngày " + FunctionYin.convertDate(en.toString()));
            for(i = 0; i <= sz; i++) {
                int total = 0;
                if(j < nameAndCounts.size() && st.plusDays(i).toString().equals(nameAndCounts.get(j).getName())){
                    total += nameAndCounts.get(j).getCount();
                    j++;
                }
                XYChart.Data<String, Number> data = new XYChart.Data<>(FunctionYin.convertDate(st.plusDays(i).toString()), total);
                series.getData().add(data);
                data.getNode().setOnMouseEntered(e -> {
                    dateInforLabel.setText("Date: " + data.getXValue());
                    revenueInforLabel.setText("Revenue: " + FunctionYin.convertMoney((Integer) data.getYValue()));
                    inforVbox.setVisible(true);
                    inforVbox.setLayoutX(e.getSceneX() - 100);
                    inforVbox.setLayoutY(e.getSceneY() - 230);
                });
                data.getNode().setOnMouseExited(e -> inforVbox.setVisible(false));
                if(max < total) max = total;
            }
            if(max > 0) {
                max = max + max / 10;
                yAxistBillProductLine.setUpperBound(max);
                max /= 11;
                yAxistBillProductLine.setTickUnit(max);
            }else {
                yAxistBillProductLine.setUpperBound(500000);
                yAxistBillProductLine.setTickUnit(50000);
            }

            // Success
//            // Test
//            try {
//                series.getNode().setStyle("-fx-stroke: black");
//            } catch (Exception e) {
//                System.out.println("TEST FALL");
//                throw new RuntimeException(e);
//            }
//            // Out Test
        }
    }
    @SuppressWarnings("unchecked")
    private void handlePerMonth(TimeRequest timeRequest) throws Exception{
        List<NameAndCount> nameAndCounts = billProductViewModel.getPerMonth(timeRequest);
        int totalProfit = 0;
        for(NameAndCount n : nameAndCounts){
            totalProfit += n.getCount();
        }if(datePickStart.getValue().until(datePickEnd.getValue(), ChronoUnit.MONTHS) >= 40) {
            settingOutTime(totalProfit);
        }else {
            sumBillProLabel.setText("Tổng doanh thu:");
            totalBillProLabel.setText(FunctionYin.convertMoney(totalProfit));
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            billProductLineChart.setData(FXCollections.observableArrayList(series));

            LocalDate st = datePickStart.getValue(), en = datePickEnd.getValue();
            int max = -1, j = 0, difMonth = (en.getYear() - st.getYear()) * 12 + en.getMonthValue() - st.getMonthValue();
            series.setName("Thống kê doanh thu từ tháng " + FunctionYin.convertDatePerMonth(st) + " tới tháng " + FunctionYin.convertDatePerMonth(en));
            for(int i = 0; i <= difMonth; i++) {
                int total = 0;
                if(j < nameAndCounts.size() && FunctionYin.convertDatePerMonth(st.plusMonths(i)).equals(FunctionYin.convertDate(nameAndCounts.get(j).getName()))) {
                    total += nameAndCounts.get(j).getCount();
                    j++;
                }
                XYChart.Data<String, Number> data = new XYChart.Data<>(FunctionYin.convertDatePerMonth(st.plusMonths(i)), total);
                series.getData().add(data);
                data.getNode().setOnMouseEntered(e -> {
                    dateInforLabel.setText("Date: " + data.getXValue());
                    revenueInforLabel.setText("Revenue: " + FunctionYin.convertMoney((Integer) data.getYValue()));
                    inforVbox.setVisible(true);
                    inforVbox.setLayoutX(e.getSceneX());
                    inforVbox.setLayoutY(e.getSceneY());
                });
                data.getNode().setOnMouseExited(e -> inforVbox.setVisible(false));
                if(max < total) max = total;
            }
            if(max > 0) {
                max = max + max / 10;
                yAxistBillProductLine.setUpperBound(max);
                max /= 11;
                yAxistBillProductLine.setTickUnit(max);
            }else {
                yAxistBillProductLine.setUpperBound(500000);
                yAxistBillProductLine.setTickUnit(50000);
            }
        }
    }
}
