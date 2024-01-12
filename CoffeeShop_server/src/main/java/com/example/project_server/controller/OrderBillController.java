package com.example.project_server.controller;

import com.example.project_server.service.OrderBillService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/orderBill")
public class OrderBillController {
    private final OrderBillService orderBillService;

    public OrderBillController(OrderBillService orderBillService) {
        this.orderBillService = orderBillService;
    }
    @PostMapping("/saveBill")
    public String createBill(@RequestBody Map<String,Object> body) throws ParseException {
        return orderBillService.saveBill(body);
    }
}
