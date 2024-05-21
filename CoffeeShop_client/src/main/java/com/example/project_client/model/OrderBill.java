package com.example.project_client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Data

public class OrderBill {
    private String id = "";
    private String buyDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    private String customerPhoneNumber = "";
    private String userStaffId = "";
    private Integer total = 0;
    private Integer received = 0;
    private Integer changeMoney = 0;
    private Boolean payMethod = true;
    private Integer deduction = 0;
    private Integer original = 0;
    private String promotionName = "";
    private List<Product> products = new ArrayList<>();

    @Data

    public static class Product {
        private Integer productId;
        private String image;
        private Integer count;
        private String name;
        private Integer price;
        @JsonCreator

        public Product(@JsonProperty("productId") Integer productId,
                       @JsonProperty("image") String image,
                       @JsonProperty("count") Integer count,
                       @JsonProperty("name") String name,
                       @JsonProperty("price") Integer price) {
            this.productId = productId;
            this.image = image;
            this.count = count;
            this.name = name;
            this.price = price;
        }
    }
}
