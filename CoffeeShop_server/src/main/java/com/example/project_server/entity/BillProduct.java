package com.example.project_server.entity;

import com.example.project_server.serializable.BillProductId;
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
@Table(name = "bill_product")
public class BillProduct {
    @EmbeddedId
    private BillProductId billProductId;

    @ManyToOne
    @JsonIgnore
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
    @JsonIgnore
    @ManyToOne
    @MapsId("billId")
    @JoinColumn(name = "bill_id")
    private OrderBill bill;
    private Integer productCount;
}
