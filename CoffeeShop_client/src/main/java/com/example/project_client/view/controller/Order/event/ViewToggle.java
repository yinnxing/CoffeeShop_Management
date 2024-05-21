package com.example.project_client.view.controller.Order.event;

import com.example.project_client.model.OrderBill;
import lombok.Getter;
import lombok.Setter;

public class ViewToggle {
    @Getter
    @Setter
    private static Boolean isCreateBill = false;
    @Getter
    @Setter
    private static OrderBill orderBill;
}
