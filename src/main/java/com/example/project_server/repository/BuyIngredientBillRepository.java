package com.example.project_server.repository;

import com.example.project_server.entity.BuyIngredientBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyIngredientBillRepository extends JpaRepository<BuyIngredientBill,String> {
}
