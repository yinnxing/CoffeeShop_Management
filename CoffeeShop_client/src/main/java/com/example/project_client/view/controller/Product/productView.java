package com.example.project_client.view.controller.Product;

import com.example.project_client.model.Product;
import com.example.project_client.repository.ProductRepository;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;

import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public final class productView {
    private static Product product;
    @FXML
    private TableColumn<Product, Integer> id;
    @FXML
    private TableColumn<Product, String> name;
    @FXML
    private TableColumn<Product, Integer> price;
    @FXML
    private TableColumn<Product, Boolean> available;
    @FXML
    private TableColumn<Product, String> image;
    @FXML
    private TextField searchTextField;
    @Getter
    private static List<Product> products;
    @FXML
    private ObservableList<Product> tableList;
    @FXML
    TableView<Product> tableView;

    public void initialize() {
        try {
            setTableView();
            setColumn();
            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue.toString().length() > 0){
                    searchTextField.setStyle("-fx-background-color:  white;");
                }
                else {
                    searchTextField.setStyle("-fx-background-color:  #f9d388;");
                }
                filterTable(convertToString(newValue));
            });
        }
        catch (Exception e){
            System.out.println("ERROR");
        }
    }
    @FXML
    private void cancel() throws IOException {
        Router.switchTo(Pages.ADMIN_VIEW);
    }
    @FXML
    private void readProduct(){
        try {
            product = tableView.getSelectionModel().getSelectedItem();
            if(product == null) {
                throw new Exception("Please choose Product to view information");
            }
            Router.switchTo(Pages.READ_PRODUCT);
        }
        catch (Exception e){
            raiseAlert(e.getMessage());
        }
    }
    @FXML
    private void addProduct() throws IOException {
        Router.switchTo(Pages.ADD_PRODUCT);
    }
    @FXML
    private void changeProduct() {
        try {
            product = tableView.getSelectionModel().getSelectedItem();
            if(product == null) {
                throw new Exception("Please choose Product to change");
            }
            Router.switchTo(Pages.CHANGE_PRODUCT);
        }
        catch (Exception e){
            raiseAlert(e.getMessage());
        }
    }
    @FXML
    private void deleteProduct() {
        try {
            product = tableView.getSelectionModel().getSelectedItem();
            if(product == null){
                throw new Exception("Please choose Product to delete");
            }
            askDelete();
        }
        catch (Exception e){
            raiseAlert(e.getMessage());
        }
    }
    private void askDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you want to delete Product");
        alert.setContentText("choose your option");

        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        String message;
        if(result.get().getButtonData() == ButtonBar.ButtonData.YES){
            try {
                ProductRepository.deleteProduct(product.getId().toString());
                tableList.remove(product);
                message = "Deleted";
            }
            catch (Exception e) {
                message = "Cannot delete";
            }
        } else {
            message = "Cancel delete";
        }
        raiseAlert(message);
    }
//    @FXML
//    private void addimg() {
//        addImage.saveImage();
//    }
    private void filterTable(String keyword) {
        ObservableList<Product> filteredList = tableList.filtered(staff ->
                convertToString(staff.getName()).toLowerCase().contains(keyword.toLowerCase())
        );
        tableView.setItems(filteredList);
    }
    public static String convertToString(String value) {
        try {
            String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    private void raiseAlert(String alertText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Notification");
        alert.setContentText(alertText);
        alert.show();
    }
    private void setTableView() throws IOException {
        products = ProductRepository.getProductsApi();
        tableList = FXCollections.observableArrayList(products);
        tableView.setItems(tableList);
    }
    private void setColumn(){
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        available.setCellValueFactory(new PropertyValueFactory<>("available"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));
    }
    public static Product getProduct() {
        return product;
    }
}
