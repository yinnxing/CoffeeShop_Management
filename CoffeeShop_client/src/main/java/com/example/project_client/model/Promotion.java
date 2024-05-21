package com.example.project_client.model;

import lombok.Data;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Promotion {
    private Integer id = 0;
    private String information = "";
    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate = LocalDate.now();
    private Boolean needCondition = false;
    private String name = "";
    private Map<Integer, Double> products = new HashMap<>();
    public static Map<String,Object> toData(Promotion promotion){
        Map<String,Object> data = new HashMap<>();
        data.put("id",promotion.getId());
        data.put("name",promotion.getName());
        data.put("information",promotion.getInformation());
        data.put("startDate",promotion.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        data.put("endDate",promotion.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        data.put("condition",promotion.getNeedCondition());
        List<Map<String,Object>> products = new ArrayList<>();
        promotion.getProducts().forEach((key,val)->{
            Map<String,Object> element = new HashMap<>();
            element.put("productId",key);
            element.put("discount",val);
            products.add(element);
        });
        data.put("products",products);
        return data;
    }
    public static Promotion fromData(Map<String,Object> data){
        if(data.isEmpty()) return null;
        Promotion promotion = new Promotion();
        promotion.setId((Integer) data.get("id"));
        promotion.setName(data.get("name").toString());
        promotion.setInformation(data.get("information").toString());
        promotion.setNeedCondition((Boolean) data.get("needCondition"));
        promotion.setStartDate(LocalDate.parse(data.get("startDate").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        promotion.setEndDate(LocalDate.parse(data.get("endDate").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        for (Map<String, Object> product : (List<Map<String, Object>>) data.get("products")) {
            promotion.getProducts().put(Integer.parseInt(product.get("productId").toString()),Double.parseDouble(product.get("discount").toString()));
        }
        return promotion;
    }
}
