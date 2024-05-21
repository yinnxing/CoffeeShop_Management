package com.example.project_client.view.controller.Staff;


import com.example.project_client.model.Staff;
import com.example.project_client.repository.StaffCalRepository;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.scene.control.*;
import lombok.Getter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.UnaryOperator;

public class AddStaffView {
    @Getter
    private Staff staff = new Staff();

    private final StaffCalRepository staffCalRepository = new StaffCalRepository();
    @FXML
    private TextField idField;
    @FXML
    private GridPane formTable;
    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;

    @FXML
    private TextField nameField;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private TextField salaryField;

    @FXML
    private TextField roleField;

//    @FXML
//    private Button clearBtn;
//
//    @FXML
//    private Button insertBtn;
//
//    @FXML
//    private Button deleteBtn;

    @FXML
    private RadioButton male;

    @FXML
    private RadioButton female;

    @FXML
    private ToggleGroup genderToggleGroup;

    public AddStaffView() {
        genderToggleGroup = new ToggleGroup();
    }
    @FXML
    public void initialize() {
        StringConverter<LocalDate> dateStringConverter = new StringConverter<>() {
            final String pattern = "dd-MM-yyyy";
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            // Xử lý khi chọn ngày
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            // Xử lý khi nhập ngày từ bàn phím
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        birthDatePicker.setConverter(dateStringConverter);

        UnaryOperator<TextFormatter.Change> nameValidationFormatter = change -> {
            if (change.getControlNewText().isEmpty()) {
                return change;
            }
            if (change.getControlNewText().matches("(([^\\d\\p{Punct}\\s]+)( )?)+")) {
                return change;
            } else {
                System.out.println(change.getText());
                System.out.println(change.getControlNewText());
                StaffView.createAlert(Alert.AlertType.WARNING, "Chỉ nhập ký tự", "", "Thông báo").show();
                change.setText("");
                return change;
            }
        };

        UnaryOperator<TextFormatter.Change> idValidationFormatter = change -> {
            if (change.getControlNewText().isEmpty()) {
                return change;
            }
            if (change.getControlNewText().matches("[0-9]{1,10}")) {
                return change;
            } else {
                StaffView.createAlert(Alert.AlertType.WARNING, "Chỉ nhập các số 0-9", "", "Thông báo").show();
                change.setText("");
                return change;
            }
        };

        nameField.setTextFormatter(new TextFormatter<>(nameValidationFormatter));
        phoneField.setTextFormatter(new TextFormatter<>(idValidationFormatter));
        salaryField.setTextFormatter(new TextFormatter<>(idValidationFormatter));
        emailField.textProperty().addListener(e -> {
            boolean active = emailField.getText()!= null && !emailField.getText().isEmpty() && !emailField.getText().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
            emailField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), active);
        });
    }
    private boolean isAllFilled() {
        for (Node n : formTable.getChildren()) {
            if (n instanceof TextField) {
                if (n.getId().equals("emailField")) {
                    continue;
                }
                if (((TextField) n).getText().isEmpty()) {
                    n.requestFocus();
                    return false;
                }
            } else if (n instanceof DatePicker) {
                if (((DatePicker) n).getValue() == null) {
                    n.requestFocus();
                    return false;
                }
            }
        }
        if (genderToggleGroup.getSelectedToggle() == null) {
            male.requestFocus();
            return false;
        }
        return true;
    }

    @FXML
    private void addStaff() throws Exception {

        if (!isAllFilled()) {
            StaffView.createAlert(Alert.AlertType.WARNING, "Chưa điền/ chọn đủ thông tin", "", "Thông báo").show();
            return;
        }

        String email = emailField.getText() == null ? "" : emailField.getText();
        if (!email.isEmpty()) {
            if (!(email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))) {
                StaffView.createAlert(Alert.AlertType.WARNING, "Email không hợp lệ", "", "Thông báo").show();
                emailField.requestFocus();
                return;
            }
        }
//        Staff newStaff = new Staff();
        staff.setId(idField.getText());
        staff.setName(nameField.getText());
        staff.setEmail(email);
        staff.setPhoneNumber(phoneField.getText());
        staff.setDob(birthDatePicker.getValue());
        staff.setAddress(addressField.getText());
        RadioButton choseBtn = (RadioButton) genderToggleGroup.getSelectedToggle();
        staff.setGender(choseBtn.getText().toLowerCase(Locale.ROOT));
        staff.setRole(roleField.getText());
        staff.setSalaryPerDay(Integer.parseInt(salaryField.getText()));
        StaffCalRepository.saveStaffApi(staff);
        StaffView.createAlert(Alert.AlertType.CONFIRMATION,"Bạn đã thêm thành công thông tin nhân viên ", "","Thông báo").show();
        Router.switchTo(Pages.STAFF_VIEW);
    }

    @FXML
    private void cancel() throws IOException {
        Router.switchTo(Pages.STAFF_VIEW);
    }

    @FXML
    private void clear() throws IOException {
        idField.clear();
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        birthDatePicker.setValue(null);
        roleField.clear();
        salaryField.clear();
        addressField.clear();
        male.setSelected(false);
        female.setSelected(false);
    }

}
