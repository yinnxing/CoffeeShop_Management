package com.example.project_client.viewModel.Order;

import com.example.project_client.model.Product;
import com.example.project_client.model.Promotion;
import com.example.project_client.repository.ProductRepository;
import com.example.project_client.repository.PromotionRepository;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
public class PromotionViewModel {
    private Integer emptyText = 0;
    private Boolean isCreate = false;
    private List<Product> products;
    private Promotion promotion;
    private final PromotionRepository promotionRepository = new PromotionRepository();

    public void initData(Promotion promotion) throws IOException {
        if(promotion!=null) {this.promotion = promotion;
           }
        else {this.promotion = new Promotion();
            isCreate = true;}
        products = ProductRepository.getProductsApi();
    }
    public void removePromo() throws IOException {
        promotionRepository.removePromotion(promotion.getId());
    }
    public Boolean check(LocalDate startDate,LocalDate endDate) throws IOException {
        return promotionRepository.checkPromotion(startDate,endDate, promotion!=null?promotion.getId():0);
    }
    public void createPromotion() throws Exception {
        promotionRepository.savePromotion(Promotion.toData(promotion));
    }
}
