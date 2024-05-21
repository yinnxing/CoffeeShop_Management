package com.example.project_client.view.controller.Order;

import com.example.project_client.event.Data;
import com.example.project_client.model.Promotion;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import com.example.project_client.view.controller.Order.components.ProductInPromotion;
import com.example.project_client.view.controller.Order.components.ProductView;
import com.example.project_client.viewModel.Order.PromotionViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PromotionView {
    @FXML
    ListView<ProductInPromotion> listView;

    @FXML
    TextField percent;
    @FXML
    FlowPane productsPane;
    @FXML
    Button addAll;

    @FXML
    Button remove;
    private Boolean isWarn = false;
    @FXML
    TextField name;
    @FXML
    TextArea information;
    @FXML
    DatePicker startTime;
    @FXML
    DatePicker endTime;
    @FXML
    CheckBox condition;
    @FXML
    VBox warningVBox;
    @FXML
    VBox parent;
    @FXML
    HBox btnCont;
    @FXML
    Button confirmation;
    @FXML
    HBox topHBox;
    @FXML
    VBox infor;
    @FXML
    TextField filter;
    private final List<ProductView> productViews = new ArrayList<>();

    private final PromotionViewModel promotionViewModel = new PromotionViewModel();

    @FXML
    void initialize() throws IOException {
        if(!(Data.getUser().getStaffId() ==null)){
            btnCont.getChildren().remove(confirmation);
            productsPane.setDisable(true);
            infor.setDisable(true);
            topHBox.setDisable(true);
        }
        parent.getChildren().remove(warningVBox);
        promotionViewModel.initData((Promotion) Router.getData(Pages.MAIN_VIEW));
        remove.setVisible(!promotionViewModel.getIsCreate()&&(Data.getUser().getStaffId() ==null));

        productsPane.getChildren().addAll(promotionViewModel.getProducts().stream().map(e -> {
            ProductView productView = new ProductView(e);
            productView.setOnMouseClicked(mouseEvent -> insertProduct(productView, Double.parseDouble(percent.getText())));
            if (promotionViewModel.getPromotion().getProducts().get(e.getId()) != null) {
                insertProduct(productView, promotionViewModel.getPromotion().getProducts().get(e.getId()));
            }
            productViews.add(productView);
            return productView;
        }).collect(Collectors.toList()));
        percent.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d*(\\.\\d*)?")) {
                percent.setText(s);
            }
            percent.setText(t1);
        }));


        name.setText(promotionViewModel.getPromotion().getName());
        information.setText(promotionViewModel.getPromotion().getInformation());
        startTime.setValue(promotionViewModel.getPromotion().getStartDate());
        endTime.setValue(promotionViewModel.getPromotion().getEndDate());
        condition.setSelected(promotionViewModel.getPromotion().getNeedCondition());
        name.textProperty().addListener((observableValue, s, t1) -> promotionViewModel.getPromotion().setName(t1));
        information.textProperty().addListener(((observableValue, s, t1) -> promotionViewModel.getPromotion().setInformation(t1)));
        startTime.valueProperty().addListener((observableValue, date, t1) -> promotionViewModel.getPromotion().setStartDate(t1));
        endTime.valueProperty().addListener(((observableValue, date, t1) -> promotionViewModel.getPromotion().setEndDate(t1)));
        condition.selectedProperty().addListener((observableValue, aBoolean, t1) -> promotionViewModel.getPromotion().setNeedCondition(t1));
        filter.textProperty().addListener(((observableValue, s, t1) -> filter(t1)));
    }

    @FXML
    void confirmation() throws Exception {


        if (showWarning()) {
            promotionViewModel.createPromotion();
            Router.switchTo(Pages.ADMIN_VIEW);
        }

    }

    @FXML
    void cancel() throws IOException {
        Router.removeData(Pages.MAIN_VIEW);
        Router.switchTo(Pages.ADMIN_VIEW);
    }

    @FXML
    void addAll() {
        if (!percent.getText().isEmpty()) {

            productsPane.getChildren().forEach(e -> {
                ProductView productView = (ProductView) e;
                insertProduct(productView, percent.getText().isEmpty() ? 10.0 : Double.parseDouble(percent.getText()));
            });
        }
    }

    private void insertProduct(ProductView product, Double percent) {
        promotionViewModel.setEmptyText(promotionViewModel.getEmptyText() + 1);
        if (!product.isDisable()) {
            promotionViewModel.getPromotion().getProducts().put(product.getProduct().getId(), percent);
            ProductInPromotion productInPromotion = new ProductInPromotion(product.getProduct(), percent);
            listView.getItems().add(productInPromotion);
            productInPromotion.getButton().setOnAction(event -> removeFromListView(product, productInPromotion));
            productInPromotion.getTextField().textProperty().addListener(((observableValue, s, t1) -> {
                if (!t1.matches("\\d*(\\.\\d*)?")) {
                    productInPromotion.getTextField().setText(s);
                }
                if (!productInPromotion.getTextField().getText().isEmpty())
                    promotionViewModel.getPromotion().getProducts().put(product.getProduct().getId(), Double.parseDouble(productInPromotion.getTextField().getText()));
                else
                    promotionViewModel.getPromotion().getProducts().put(product.getProduct().getId(), percent);
            }));

            product.setDisable(true);
        }
    }

    private void removeFromListView(ProductView productView, ProductInPromotion productInPromotion) {
        productView.setDisable(false);
        listView.getItems().remove(productInPromotion);
    }

    @FXML
    public void remove() throws IOException {
        promotionViewModel.removePromo();
        Router.switchTo(Pages.ADMIN_VIEW);
    }

    private Boolean showWarning() throws IOException {
        if (name.getText().isEmpty()
                || information.getText().isEmpty()
                || startTime.getValue().isAfter(endTime.getValue())
               // || !promotionViewModel.check(startTime.getValue(),endTime.getValue())
                || listView.getItems().isEmpty()) {
            if (!isWarn) {
                parent.getChildren().add(warningVBox);
                isWarn = true;
            }
            return false;
        }
        return true;
    }
    private void filter(String value) {
        productsPane.getChildren().clear();
        productsPane.getChildren().addAll(productViews.stream().filter(e ->
                e.getProduct().getName().toLowerCase().contains(value)
        ).collect(Collectors.toList()));
    }
}
