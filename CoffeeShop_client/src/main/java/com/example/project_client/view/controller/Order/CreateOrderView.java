package com.example.project_client.view.controller.Order;

import atlantafx.base.theme.Styles;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import com.example.project_client.view.controller.Order.components.ProductCount;
import com.example.project_client.view.controller.Order.components.ProductView;
import com.example.project_client.view.controller.Order.event.ViewToggle;
import com.example.project_client.view.controller.Order.interfaces.InitStyles;
import com.example.project_client.viewModel.Order.CreateOrderBillViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CreateOrderView implements InitStyles {
    @FXML
    FlowPane productsPane;
    @FXML
    ListView<ProductCount> listView;
    @FXML
    Label total;
    @FXML
    Label deduction;
    @FXML
    Label original;
    @FXML
    VBox promotionContainer;
    @FXML
    Button create;
    @FXML
    TextField money;
    @FXML
    ComboBox<String> method;
    @FXML
    Label receiveTotalWarn;

    @FXML
    Label dobNotify;
    @FXML
    Label totalNotify;
    @FXML
    Label moneyWarn;
    @FXML
    Label productWarn;

    @FXML
    VBox notifyContainer;
    @FXML
    Button back;

    @FXML
    TextField filter;
    private final List<ProductView> productViews = new ArrayList<>();
    @FXML
    CreateOrderBillViewModel createOrderBillViewModel = new CreateOrderBillViewModel();

    @FXML
    void initialize() throws IOException {
        createOrderBillViewModel.initData();
        initStyle();
        method.getItems().addAll("Chuyển khoản", "Tiền mặt");
        method.getSelectionModel().selectFirst();
        method.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> createOrderBillViewModel.getOrderBill().setPayMethod(t1.equals("Tiền mặt")));
        notifyContainer.getChildren().clear();

        createOrderBillViewModel.getDobNotify().addListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                if (!notifyContainer.getChildren().contains(dobNotify)) {
                    notifyContainer.getChildren().add(dobNotify);
                }
            } else notifyContainer.getChildren().remove(dobNotify);
        });
        createOrderBillViewModel.getTotalNotify().addListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                if (!notifyContainer.getChildren().contains(totalNotify)) {
                    notifyContainer.getChildren().add(totalNotify);
                }
            } else notifyContainer.getChildren().remove(totalNotify);
        });
        createOrderBillViewModel.getTotal().addListener((observable, oldVal, newVal) -> money.setPromptText(NumberFormat.getNumberInstance(Locale.US).format(newVal.intValue())));
        money.textProperty().addListener(((observableValue, oldVal, newVal) -> {
            String val = newVal;
            if (!newVal.matches("\\d*")) {
                val = newVal.replaceAll("\\D", "");
            }
            if (!val.isEmpty()) {
                money.setText(NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(val)));
                createOrderBillViewModel.getOrderBill().setReceived(Integer.parseInt(val));
            } else {
                money.setText(val);
            }
        }));

        if (createOrderBillViewModel.getPromotion() != null) {
            promotionContainer.getChildren().add(insertPromotion());
        }
        createOrderBillViewModel.getProducts().forEach(product -> {
            ProductView productView = new ProductView(product);
            productViews.add(productView);
            productView.setOnMouseClicked(e -> {
                if (createOrderBillViewModel.check(product)) {
                    ProductCount productCount = new ProductCount(product);
                    productCount.getTextField().textProperty().addListener(((observableValue1, s, t11) -> {
                        if (!t11.matches("\\d*")) {
                            t11 = t11.replaceAll("\\D", "");
                        }
                        if (t11.isEmpty()) t11 = "1";
                        productCount.getTextField().setText(t11);
                        createOrderBillViewModel.getCount().get(product).setValue(Integer.parseInt(t11));
                    }));
                    createOrderBillViewModel.initCount(product);
                    createOrderBillViewModel.getCount().get(product).addListener(((observableValue, number, t1) -> {
                                if (t1.intValue() == 0) listView.getItems().remove(productCount);
                                else {
                                    productCount.getTextField().setText(t1.toString());
                                    productCount.getLabel2().setText(NumberFormat.getNumberInstance(Locale.US).format(product.getPrice() * t1.longValue()) + " VND");
                                }
                            })
                    );
                    productCount.getSub().setOnAction((actionEvent) -> createOrderBillViewModel.reduceProduct(product));
                    productCount.getAdd().setOnAction((actionEven) -> createOrderBillViewModel.addMoreProduct(product));
                    listView.getItems().add(productCount);
                } else createOrderBillViewModel.addMoreProduct(product);
            });
            productsPane.getChildren().add(productView);
        });
        createOrderBillViewModel.getTotal().addListener((obs, oldVal, newVal) -> total.setText(NumberFormat.getNumberInstance(Locale.US).format(newVal.intValue()) + " VND"));
        createOrderBillViewModel.getDeduction().addListener((obs, oldVal, newVal) -> deduction.setText("- " + NumberFormat.getNumberInstance(Locale.US).format(newVal.intValue()) + " VND"));
        createOrderBillViewModel.getOriginal().addListener((obs, oldVal, newVal) -> original.setText(NumberFormat.getNumberInstance(Locale.US).format(newVal.intValue()) + " VND"));
        Router.setData(Pages.CREATE_ORDER_VIEW, createOrderBillViewModel.getOrderBill());
        filter.textProperty().addListener(((observableValue, s, t1) -> filter(t1)));

    }

    @FXML
    public void showConfirmDialogView() throws IOException {


        Router.setData(Pages.CREATE_ORDER_VIEW, createOrderBillViewModel);
        Router.showDialog(Pages.CUSTOMER_INFORMATION_INPUT_VIEW);
    }

    @FXML
    public void cancel() throws IOException {
        Router.switchTo(Pages.ADMIN_VIEW);
    }


    @FXML
    public void confirm() throws Exception {
        if (validate()) {
            if (!createOrderBillViewModel.getCustomer().getPhoneNumber().isEmpty()) {
                createOrderBillViewModel.getCustomer().setTotal(createOrderBillViewModel.getCustomer().getTotal() + createOrderBillViewModel.getOrderBill().getTotal());
                createOrderBillViewModel.saveCustomer();

            }
            createOrderBillViewModel.setProductOfOrderBill();
            ViewToggle.setIsCreateBill(true);
            ViewToggle.setOrderBill(createOrderBillViewModel.getOrderBill());
            Router.switchTo(Pages.ORDER_BILL_VIEW);
        }
    }

    @Override
    public void initStyle() {
        back.setGraphic(new FontIcon(Material2AL.KEYBOARD_ARROW_LEFT));
        Styles.toggleStyleClass(listView, Styles.BORDERED);
    }

    public HBox insertPromotion() {
        HBox hBox = new HBox();
        Label label = new Label(createOrderBillViewModel.getPromotion().getName());
        Region region = new Region();
        CheckBox checkBox = new CheckBox("Áp dụng");
        checkBox.selectedProperty().addListener((observable -> {
            if (checkBox.isSelected()) {
                createOrderBillViewModel.setApplyPromotion(true);
                createOrderBillViewModel.updatePromotion();
            } else {
                createOrderBillViewModel.setApplyPromotion(false);
                createOrderBillViewModel.resetPromotion();
            }
        }));
        if (!createOrderBillViewModel.getPromotion().getNeedCondition()) {
            checkBox.setSelected(true);
            checkBox.setDisable(true);
        }
        hBox.getChildren().addAll(label, region, checkBox);
        HBox.setHgrow(region, Priority.ALWAYS);

        return hBox;
    }

    private Boolean validate() {

        if(createOrderBillViewModel.getOrderBill().getReceived()<createOrderBillViewModel.getOrderBill().getTotal()){
            if (!notifyContainer.getChildren().contains(receiveTotalWarn)) notifyContainer.getChildren().add(receiveTotalWarn);
            return false;
        } else notifyContainer.getChildren().remove(receiveTotalWarn);
        if (money.getText().isEmpty()) {
            if (!notifyContainer.getChildren().contains(moneyWarn)) notifyContainer.getChildren().add(moneyWarn);
            return false;
        } else notifyContainer.getChildren().remove(moneyWarn);
        if (listView.getItems().isEmpty()) {
            if (!notifyContainer.getChildren().contains(productWarn)) notifyContainer.getChildren().add(productWarn);
            return false;
        } else notifyContainer.getChildren().remove(productWarn);
        return true;
    }

    private void filter(String value) {
        productsPane.getChildren().clear();
        productsPane.getChildren().addAll(productViews.stream().filter(e ->
                e.getProduct().getName().toLowerCase().contains(value)
        ).collect(Collectors.toList()));
    }
}
