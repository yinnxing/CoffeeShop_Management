package com.example.project_client.view.controller.Order;

import atlantafx.base.controls.Card;
import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import com.example.project_client.event.Data;
import com.example.project_client.model.OrderBill;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import com.example.project_client.view.controller.Order.components.PromotionChild;
import com.example.project_client.view.controller.Order.event.ViewToggle;
import com.example.project_client.view.controller.Order.interfaces.InitStyles;
import com.example.project_client.viewModel.Order.MainViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2MZ;

import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainView implements InitStyles {
    @FXML
    Pagination pagination;

    @FXML
    ListView<PromotionChild> listView;
    @FXML
    TextField filter;
    @FXML
    Button createOrder;
    @FXML
    Button createPromotion;
    private final MainViewModel mainViewModel = new MainViewModel();

    @FXML
    void initialize() throws IOException {
        if(!(Data.getUser().getStaffId() ==null)){
            createPromotion.setVisible(false);
        } else {
            createOrder.setVisible(false);
        }
        mainViewModel.initModel();
        mainViewModel.getPromotions().forEach(e -> listView.getItems().add(new PromotionChild(e)));
        filter.textProperty().addListener((observableValue, s, t1) -> filter(mainViewModel.getOrderBills(), t1));
        filter(mainViewModel.getOrderBills(), "");

    }


    @FXML
    public void switchToCreateOrderView() throws IOException {
        Router.switchTo(Pages.CREATE_ORDER_VIEW);
    }


    @FXML
    void create() throws IOException {
        Router.switchTo(Pages.PROMOTION_VIEW);
    }

    private void filter(List<OrderBill> orderBillsList, String value) {
        initStyle();
        List<OrderBill> orderBills = orderBillsList.stream().filter(e -> e.getId().toLowerCase().contains(value)).collect(Collectors.toList());
        int size = orderBills.size();
        pagination.setPageCount(size % 4 == 0 ? size / 4 : (size / 4 + 1));
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory(index -> {
            VBox vBox = new VBox();
            IntStream.range(index * 4, index * 4 + 4).forEach(e -> {
                        if (e < size) vBox.getChildren().add(new MyCard(orderBills.get(e)));
                    }
            );
            return vBox;
        });
    }



    @Override
    public void initStyle() {
        createOrder.getStyleClass().add(Styles.ACCENT);
        createOrder.setGraphic(new FontIcon(Material2MZ.PLUS));
    }
}

@Getter
class MyCard extends Card {
    private final OrderBill orderBill;

    public MyCard(OrderBill orderBill) {
        this.orderBill = orderBill;
        getStyleClass().add(Styles.ELEVATED_2);
        setMinWidth(250);
        setHeader(new Tile(
                orderBill.getId(),
                orderBill.getUserStaffId()
        ));
        setBody(new Label(orderBill.getBuyDate()));
        setOnMouseClicked(e -> {
            try {
                gotoOrderBillView(orderBill);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void gotoOrderBillView(OrderBill orderBill) throws IOException {
        ViewToggle.setIsCreateBill(false);
        ViewToggle.setOrderBill(orderBill);
        Router.switchTo(Pages.ORDER_BILL_VIEW);
    }
}