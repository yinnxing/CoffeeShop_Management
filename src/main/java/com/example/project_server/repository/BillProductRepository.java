package com.example.project_server.repository;

import com.example.project_server.entity.BillProduct;
import com.example.project_server.serializable.BillProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillProductRepository extends JpaRepository<BillProduct, BillProductId> {
}
