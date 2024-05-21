package com.example.project_client.viewModel.Order;


import com.example.project_client.model.OrderBill;
import com.example.project_client.model.Promotion;
import com.example.project_client.repository.OrderBillRepository;
import com.example.project_client.repository.PromotionRepository;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainViewModel {
    private final PromotionRepository promotionRepository = new PromotionRepository();
    private final OrderBillRepository orderBillRepository = new OrderBillRepository();
    @Getter
    private List<Promotion> promotions = new ArrayList<>();
    @Getter
    private List<OrderBill> orderBills=new ArrayList<>();
    public void initModel() throws IOException {
        loadPromotions();
        orderBills = orderBillRepository.getAll();
    }
    private void loadPromotions() throws IOException {
        promotionRepository.getAllPromotion().forEach(e-> promotions.add(Promotion.fromData(e)));
    }

}
