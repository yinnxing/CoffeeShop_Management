package com.example.project_client.view.controller.Revenue;

import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import com.example.project_client.model.SalaryCal;
import com.example.project_client.model.TimeAndNameRequest;
import com.example.project_client.model.TimeRequest;
import com.example.project_client.view.controller.Revenue.Function.FunctionYin;
import com.example.project_client.viewModel.Revenue.SalaryCalViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SalaryCalViewController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idStaffColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameStaffColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        salaryStaffColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        FunctionYin.validColumnMoney(salaryStaffColumn);
        FunctionYin.validDatePicker(datePickStart);
        FunctionYin.validDatePicker(datePickEnd);
        FunctionYin.addBtnReturn(returnHbox);
        Button salaryCalBtn = new Button("Tính");
        salaryCalBtn.getStyleClass().addAll(Styles.BUTTON_OUTLINED, Styles.SUCCESS, Styles.LARGE);
        salaryCalBtn.setPrefWidth(200);
        salaryCalBtn.setOnAction(e -> {
            try {
                salaryCal();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        inputHbox.getChildren().add(salaryCalBtn);
        initStyle();
        idStaffTf.focusedProperty().addListener((observable, oldV, newV) -> idStaffTf.pseudoClassStateChanged(Styles.STATE_SUCCESS, newV));
    }
    @FXML
    private HBox returnHbox;
    @FXML
    private HBox inputHbox;
    @FXML
    private VBox idStaffVbox;
    @FXML
    private Label searchIdLabel;
    @FXML
    private TextField idStaffTf;
    @FXML
    private Label dateStartLabel;
    @FXML
    private DatePicker datePickStart;
    @FXML
    private Label dateEndLabel;
    @FXML
    private DatePicker datePickEnd;
    @FXML
    private VBox parentTotalVbox;
    @FXML
    private Label sumSalaryLabel;
    @FXML
    private Label totalSalaryLabel;
    @FXML
    private TableView<SalaryCal> tableSalary;
    @FXML
    TableColumn<SalaryCal, String> idStaffColumn;
    @FXML
    TableColumn<SalaryCal, String> nameStaffColumn;
    @FXML
    TableColumn<SalaryCal, Integer> salaryStaffColumn;

    private final SalaryCalViewModel salaryCalViewModel = new SalaryCalViewModel();
    private void popAlert(int type){
        tableSalary.setVisible(false);
        parentTotalVbox.setVisible(false);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(type == 1){
            alert.setContentText("Ngày bắt đầu không được phép lớn hơn ngày kết thúc!");
        }else{
            alert.setContentText("Đã có lỗi xảy ra, vui lòng thử lại sau!");
        }
        alert.showAndWait();
    }

    public void salaryCal() {
        idStaffVbox.requestFocus();
        if(datePickStart.getValue().isAfter(datePickEnd.getValue())){
            popAlert(1);
            return;
        }
        try {
            ScrollBar vScrollBar = (ScrollBar) tableSalary.lookup(".scroll-bar:vertical");
            vScrollBar.setValue(vScrollBar.getMin());
            if(idStaffTf.getText() == null || idStaffTf.getText().trim().isEmpty()){
                tableSalary.setItems(FXCollections.observableList(salaryCalViewModel.getAllSalary(new TimeRequest(datePickStart.getValue(), datePickEnd.getValue()))));
                idStaffTf.setText("");
            }else{
                tableSalary.setItems(FXCollections.observableList(salaryCalViewModel.getBySearching(new TimeAndNameRequest(idStaffTf.getText(), datePickStart.getValue(), datePickEnd.getValue()))));
            }

            tableSalary.setVisible(true);
            int totalSalary = 0;
            for(SalaryCal sCD : tableSalary.getItems()) {
                totalSalary += sCD.getSalary();
            }
            if(idStaffTf.getText() == null || idStaffTf.getText().trim().isEmpty()) {
                totalSalaryLabel.setText(FunctionYin.convertMoney(totalSalary));
                parentTotalVbox.setVisible(true);
            }else {
                parentTotalVbox.setVisible(false);
            }
        } catch (Exception e) {
            popAlert(0);
            throw new RuntimeException(e);
        }
    }
    private void initStyle(){
        FunctionYin.validLabel(searchIdLabel);
        FunctionYin.validLabel(dateStartLabel);
        FunctionYin.validLabel(dateEndLabel);
        FunctionYin.validLabel(sumSalaryLabel);
        FunctionYin.validLabel(totalSalaryLabel);
        idStaffTf.getStyleClass().add(Styles.TITLE_4);
        datePickStart.getStyleClass().add(Styles.TITLE_4);
        datePickEnd.getStyleClass().add(Styles.TITLE_4);
        tableSalary.getStyleClass().add(Tweaks.EDGE_TO_EDGE);
    }
}
