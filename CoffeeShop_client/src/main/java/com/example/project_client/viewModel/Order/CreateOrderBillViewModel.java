package com.example.project_client.viewModel.Order;

import com.example.project_client.model.Customer;
import com.example.project_client.model.OrderBill;
import com.example.project_client.model.Product;
import com.example.project_client.model.Promotion;
import com.example.project_client.repository.CustomerRepository;
import com.example.project_client.repository.ProductRepository;
import com.example.project_client.repository.PromotionRepository;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CreateOrderBillViewModel {
    private final PromotionRepository promotionRepository = new PromotionRepository();
    @Getter
    private List<Product> products;
    @Getter
    private OrderBill orderBill = new OrderBill();
    @Getter
    private Map<Product, SimpleIntegerProperty> count = new HashMap<>();
    @Getter
    private final SimpleIntegerProperty original = new SimpleIntegerProperty(0);
    @Getter
    private final SimpleIntegerProperty total = new SimpleIntegerProperty(0);
    @Getter
    private final SimpleIntegerProperty deduction = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty deductionForCustomer = new SimpleIntegerProperty(0);
    @Getter
    private final SimpleBooleanProperty dobNotify = new SimpleBooleanProperty(false);
    @Getter
    private final SimpleBooleanProperty totalNotify = new SimpleBooleanProperty(false);
    @Getter
    private Promotion promotion;
    @Setter
    private Boolean applyPromotion = false;
    @Setter
    @Getter
    private Customer customer = new Customer();
    private final CustomerRepository customerRepository = new CustomerRepository();

    public void initData() throws IOException {
        original.addListener(((observableValue, number, t1) -> orderBill.setOriginal(t1.intValue())));
        deduction.addListener(((observableValue, number, t1) -> orderBill.setDeduction(t1.intValue())));
        total.addListener(((observableValue, number, t1) -> orderBill.setTotal(t1.intValue())));

        deductionForCustomer.addListener((observableValue, oldVal, newVal) -> {
            deduction.setValue(deduction.getValue() + original.getValue() * (newVal.intValue() - oldVal.intValue()) / 100);
            total.setValue(original.getValue() - deduction.getValue());
        });

        products = ProductRepository.getProductsApi();
        promotion = Promotion.fromData(promotionRepository.getPromotionByDate(LocalDate.now()));
    }

    public void addMoreProduct(Product product) {
        count.get(product).set(count.get(product).getValue() + 1);
        original.setValue(original.getValue() + product.getPrice());
        deduction.setValue(deduction.getValue() + product.getPrice() * deductionForCustomer.doubleValue() / 100);
        if (promotion != null && applyPromotion)
            if (promotion.getProducts().get(product.getId()) != null) {
                deduction.setValue(deduction.getValue() + product.getPrice() * promotion.getProducts().get(product.getId()) / 100);
            }
        total.setValue(original.getValue() - deduction.getValue());
    }

    public void reduceProduct(Product product) {
        count.get(product).set(count.get(product).getValue() - 1);
        original.setValue(original.getValue() - product.getPrice());
        if (count.get(product).getValue() == 0) {
            count.remove(product);
        }
        deduction.setValue(deduction.getValue() - product.getPrice() * deductionForCustomer.doubleValue() / 100);
        if (promotion != null && applyPromotion)
            if (promotion.getProducts().get(product.getId()) != null) {
                deduction.setValue(deduction.getValue() - product.getPrice() * promotion.getProducts().get(product.getId()) / 100);
            }
        total.setValue(original.getValue() - deduction.getValue());
    }

    public void initCount(Product product) {
        count.put(product, new SimpleIntegerProperty(0));
        addMoreProduct(product);
    }

    public boolean check(Product product) {
        return count.get(product) == null;
    }

    public void setProductOfOrderBill() {
        orderBill.getProducts().clear();
        count.forEach((product, simpleIntegerProperty) -> orderBill.getProducts().add(new OrderBill.Product(product.getId(), product.getImage(), simpleIntegerProperty.intValue(), product.getName(), product.getPrice())));
    }

    public void resetPromotion() {
        orderBill.setPromotionName("");
        deduction.setValue(original.intValue() * deductionForCustomer.intValue() / 100);
        total.set(original.getValue() - deduction.getValue());
    }

    public void updatePromotion() {
        orderBill.setPromotionName("");
        deduction.setValue(0);
        count.forEach(((product, simpleIntegerProperty) -> {
            if (promotion.getProducts().get(product.getId()) != null) {
                deduction.setValue(deduction.getValue() + simpleIntegerProperty.intValue() * (promotion.getProducts().get(product.getId()) * product.getPrice() / 100));
            }
        }));
        deduction.setValue(deduction.getValue() + original.intValue() * deductionForCustomer.intValue() / 100);
        total.setValue(original.getValue() - deduction.getValue());
        orderBill.setPromotionName(promotion.getName());
    }

    public void saveCustomer() throws Exception {
        customerRepository.saveCustomer(customer);
    }

    public void setCustomerPhone() {
        orderBill.setCustomerPhoneNumber(customer.getPhoneNumber().isEmpty() ? "" : customer.getPhoneNumber());
    }

    public void checkCustomerForPromo(LocalDate localDate) {
        deductionForCustomer.setValue(0);
        if (customer.getTotal() > 500000 && !customer.getPhoneNumber().isEmpty()) {
            totalNotify.setValue(true);
            deductionForCustomer.setValue(deductionForCustomer.getValue() + 10);
        } else totalNotify.setValue(false);

        if (localDate.format(DateTimeFormatter.ofPattern("MM-dd")).equals(LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd"))) && !customer.getPhoneNumber().isEmpty()) {
            deductionForCustomer.setValue(deductionForCustomer.getValue() + 10);
            dobNotify.setValue(true);
        } else dobNotify.setValue(false);
    }
}
