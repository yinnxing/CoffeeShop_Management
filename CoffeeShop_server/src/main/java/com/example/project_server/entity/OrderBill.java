package com.example.project_server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "order_bill")
public class OrderBill {
    @Id
    private String id;
    private Date buyDate;
    private String customerPhoneNumber;
    private String userStaffId;
    private Integer total;
}
