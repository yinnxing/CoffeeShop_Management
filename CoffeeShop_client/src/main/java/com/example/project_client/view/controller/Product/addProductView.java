package com.example.project_client.view.controller.Product;

import java.io.File;
import java.io.IOException;

import com.example.project_client.model.Product;
import com.example.project_client.repository.ProductRepository;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import lombok.Getter;


public class addProductView {
    @FXML
    private AnchorPane main_form;
    @Getter
    private Product product = new Product();
    @FXML
    private ChoiceBox<Boolean> choiceBox;
    @FXML
    private Button image;
    @FXML
    private TextField name, price;
    @FXML
    private Label nameAlert, priceAlert, imageAlert;
    private final Boolean[] available = {Boolean.FALSE, Boolean.TRUE};
    private Boolean[] check = {true, false, false, true, false, true};
    @FXML
    public void initialize() throws Exception {
        choiceBox.getItems().addAll(available);
        product.setAvailable(Boolean.TRUE);
        System.out.println("Add product");
        setField();
    }
    @FXML
    public void cancel() throws IOException {
        raiseAlert("Cancel add product");
        Router.switchTo(Pages.PRODUCT_VIEW);
    }
    @FXML
    public void confirm() throws Exception {
        try {
            for(int i = 0; i < 6; ++i) {
                if (!check[i]) {
                    throw new Exception("Invalid Field");
                }
            }
            product.setAvailable(choiceBox.getSelectionModel().getSelectedItem());
            ProductRepository.saveProduct(product);
            raiseAlert("Added Product");
            Router.switchTo(Pages.PRODUCT_VIEW);
        }
        catch (Exception e) {
            raiseAlert(e.getMessage());
        }
    }
    private void raiseAlert(String alertText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Notification");
        alert.setContentText(alertText);
        alert.show();
    }
    private void setField() {
        setName();
        setPrice();
        choiceBox.setValue(Boolean.TRUE);
//        choiceBox.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" +   "-fx-mark-color: orange;");
    }
    private void setName(){
        name.setPromptText("input name");
        name.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                if(!product.setName(newValue)){
                    throw new Exception("Invalid name, name must be a String");
                }
                check[1] = true;
                nameAlert.setText("");
            }
            catch (Exception e) {
                check[1] = false;
                nameAlert.setText(e.getMessage());
            }
        });
    }

    private void setPrice() {
        price.setPromptText("input price");
        price.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                try {
                    if (!product.setPrice(Integer.parseInt(newValue))) {
                        throw new Exception();
                    }
                }
                catch (Exception e) {
                    throw new Exception("Invalid price, price must be a number from 0 to 1000000");
                }
                check[2] = true;
                priceAlert.setText("");
            }
            catch (Exception e){
                check[2] = false;
                priceAlert.setText(e.getMessage());
            }
        });
    }

    @FXML
    private void chooseImage(){
        try {
            FileChooser openFile = new FileChooser();
            openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*.png", "*.jpg"));
            File file = openFile.showOpenDialog(main_form.getScene().getWindow());
            if (file != null) {
                System.out.println("Selected file: " + file.getAbsolutePath());
                String directory = System.getProperty("user.dir") + "/src/main/resources/com/example/project_client/images";
                String imageString = file.getAbsolutePath().replace(System.getProperty("user.dir")+"/src/main/resources", "");
                product.setImage(imageString);
                image.setText(imageString);
                imageAlert.setText("");
                check[4] = true;
            }
//            JFileChooser fileChooser = new JFileChooser();
//            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "png", "jpg");
//            fileChooser.setFileFilter(filter);
//            String directory = System.getProperty("user.dir") + "/src/main/resources/com/example/project_client/images";
//            System.out.println("direct: " + directory);
//            int result = fileChooser.showOpenDialog(null);
//            System.out.println("Result " + result);
//            JFileChooser fileChooser = new JFileChooser(directory);
//            int result = fileChooser.showOpenDialog(null);


//            if (result == JFileChooser.APPROVE_OPTION) {
//                File selectegetAFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
//                System.out.println("FilePath " + selectedFile);
//                if (!Desktop.isDesktopSupported()) {
//                    System.out.println("Not supported");
//                }
//                else {
//                    Desktop desktop = Desktop.getDesktop();
//                    product.setImage(selectedFile.toString().repdireclace(System.getProperty("user.dir") + "\\src\\main\\resources", "").replace("\\", "/"));
//                    image.setText(selectedFile.toString().replace(directory+"\\", ""));
//                    imageAlert.setText("");
//                    check[4] = true;
//                }
//            } else if (result == JFileChooser.CANCEL_OPTION) {
//                System.out.println("Cancelled");
//            }
        }
        catch (Exception e) {
            System.out.println("ERROR");
        }
    }
}
