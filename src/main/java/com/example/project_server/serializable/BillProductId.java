package com.example.project_server.serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class BillProductId implements Serializable {
    @Column(name = "bill_id")
    String billId;
    @Column(name="product_id")
    Integer productId;
}
