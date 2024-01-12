package com.example.project_server.repository;

import com.example.project_server.entity.OrderBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBillRepository extends JpaRepository<OrderBill,String> {
}
