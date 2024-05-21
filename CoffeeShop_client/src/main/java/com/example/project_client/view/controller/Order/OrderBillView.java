package com.example.project_client.view.controller.Order;



import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import com.example.project_client.view.controller.Order.event.ViewToggle;
import com.example.project_client.viewModel.Order.OrderBillViewModel;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;


public class OrderBillView {
    @FXML
    Label buyDate;
    @FXML
    Label customerPhone;
    @FXML
    Label billCode;
    @FXML
    Label staffId;
    @FXML
    Label deduction;
    @FXML
    Label original;
    @FXML
    Label total;
    @FXML
    Label received;
    @FXML
    Label change;
    @FXML
    Label method;
    @FXML
    TableView<TableRow> productsContainer;
    @FXML
    TableColumn<TableRow, String> name;
    @FXML
    TableColumn<TableRow, String> price;
    @FXML
    TableColumn<TableRow, Integer> count;
    @FXML
    TableColumn<TableRow, ImageView> image;
    @FXML
    TableColumn<TableRow, String> totalPrice;
    @FXML
    Button createNewBill;
    @FXML
    HBox hBox;
    private final OrderBillViewModel orderBillViewModel = new OrderBillViewModel();

    @FXML
    void initialize() throws Exception {
        if(!ViewToggle.getIsCreateBill()) hBox.getChildren().remove(createNewBill);
        name.setCellValueFactory(cellData -> cellData.getValue().getProductName());
        price.setCellValueFactory(cellData -> cellData.getValue().getPrice());
        image.setCellValueFactory(cellData -> cellData.getValue().getImageView());
        count.setCellValueFactory(cellData -> cellData.getValue().getCount().asObject());
        totalPrice.setCellValueFactory(cellData -> cellData.getValue().getTotal());

        orderBillViewModel.initData(ViewToggle.getOrderBill(), ViewToggle.getIsCreateBill());

        buyDate.setText(orderBillViewModel.getData().getBuyDate());
        customerPhone.setText(orderBillViewModel.getData().getCustomerPhoneNumber());
        billCode.setText(orderBillViewModel.getData().getId());
        staffId.setText(orderBillViewModel.getData().getUserStaffId());
        total.setText(NumberFormat.getNumberInstance(Locale.US).format(orderBillViewModel.getData().getTotal()) + " VND");
        method.setText("Khách trả (" + (orderBillViewModel.getData().getPayMethod() ? "Quét mã QR" : "Tiền mặt") + "):");
        received.setText(NumberFormat.getNumberInstance(Locale.US).format(orderBillViewModel.getData().getReceived()) + " VND");
        change.setText(NumberFormat.getNumberInstance(Locale.US).format(orderBillViewModel.getData().getChangeMoney()) + " VND");
        deduction.setText("- " + NumberFormat.getNumberInstance(Locale.US).format(orderBillViewModel.getData().getDeduction()) + " VND");
        original.setText(NumberFormat.getNumberInstance(Locale.US).format(orderBillViewModel.getData().getOriginal()) + " VND");
        orderBillViewModel.getData().getProducts().forEach((product) -> productsContainer.getItems().add(new TableRow(
                product.getName(),
                product.getImage(),
                NumberFormat.getNumberInstance(Locale.US).format(product.getPrice()),
                 product.getCount(),
                NumberFormat.getNumberInstance(Locale.US).format((long) product.getCount() * product.getPrice())))
        );
    }

    @FXML
    public void done() throws IOException {
        Router.switchTo(Pages.ADMIN_VIEW);
    }
    @FXML
    public void createNewBill() throws IOException {
        Router.switchTo(Pages.CREATE_ORDER_VIEW);
    }
//    @FXML
//    public void createNewBill() throws IOException {
//        Router.switchTo(Pages.CREATE_ORDER_VIEW);
//    }
}

@Getter
@Setter
class TableRow {
    private final StringProperty productName;
    private final StringProperty price;
    private final ObjectProperty<ImageView> imageView;
    private final IntegerProperty count;
    private final StringProperty total;

    public TableRow(String productName, String imageUrl, String price, Integer count, String total) {
        this.productName = new SimpleStringProperty(productName);
        this.price = new SimpleStringProperty(price);
        this.count = new SimpleIntegerProperty(count);
        this.total = new SimpleStringProperty(total);
        this.imageView = new SimpleObjectProperty<>(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imageUrl)))));
        imageView.get().setFitHeight(150.0);
        imageView.get().setFitWidth(150.0);
    }
}
