package com.example.project_client.view.controller.Customer;

import com.example.project_client.event.Data;
import com.example.project_client.model.User;
import com.example.project_client.repository.UserRepository;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;


public class LoginView {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label invalidLoginLabel;

    @FXML
    private void handleLoginButton(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate empty fields
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            invalidLoginLabel.setText("Please enter username/password.");
            return;
        }

        List<User> listUser = new UserRepository().getAllUsers();

        boolean foundUser = false;


        User thisUser = new User();

        for (User user : listUser) {
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                foundUser = true;
                thisUser = user;
            }
        }

        if (foundUser) {
            Data.setUser(thisUser);
            Router.goTo(Pages.ADMIN_VIEW);

        } else {
            showErrorAlert("Đăng nhập thất bại", "Vui lòng thử lại!");
        }
    }


    @FXML
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
