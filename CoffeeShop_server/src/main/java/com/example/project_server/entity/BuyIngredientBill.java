package com.example.project_server.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(name = "buy_ingredient_bill")
public class BuyIngredientBill {
    @Id
    private String id;
    private Date buyDate;
    private Integer price;
}
