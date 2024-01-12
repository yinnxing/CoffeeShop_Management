package com.example.project_server.entity;

import com.example.project_server.serializable.BillIngredientId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(name = "bill_ingredient")
public class BillIngredient {
    @EmbeddedId
    private BillIngredientId billIngredientId;

    @ManyToOne
    @JsonIgnore
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
    @JsonIgnore
    @ManyToOne
    @MapsId("billId")
    @JoinColumn(name = "bill_id")
    private BuyIngredientBill bill;
}
