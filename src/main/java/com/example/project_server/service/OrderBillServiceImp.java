package com.example.project_server.service;

import com.example.project_server.entity.BillProduct;
import com.example.project_server.entity.OrderBill;
import com.example.project_server.repository.BillProductRepository;
import com.example.project_server.repository.OrderBillRepository;
import com.example.project_server.repository.ProductRepository;
import com.example.project_server.serializable.BillProductId;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Map;
@Service
public class OrderBillServiceImp implements OrderBillService{
    private final BillProductRepository billProductRepository;
    private final OrderBillRepository orderBillRepository;
    private final ProductRepository productRepository;

    public OrderBillServiceImp(BillProductRepository billProductRepository, OrderBillRepository orderBillRepository, ProductRepository productRepository) {
        this.billProductRepository = billProductRepository;
        this.orderBillRepository = orderBillRepository;
        this.productRepository = productRepository;
    }

    @Override
    public String saveBill(Map<String, Object> bill) throws ParseException {
        OrderBill orderBill = new OrderBill();
        orderBill.setId(bill.get("id").toString());
        orderBill.setTotal(Integer.parseInt(bill.get("total").toString()));
        orderBill.setBuyDate((new SimpleDateFormat("dd/MM/yyyy").parse(bill.get("buyDate").toString())));
        orderBill.setCustomerPhoneNumber(bill.get("customerPhone").toString());
        orderBill.setUserStaffId(bill.get("userStaffId").toString());
        orderBillRepository.save(orderBill);
        for(Map<String,Object> product : (List<Map<String,Object>>) bill.get("products")){
            BillProduct billProduct = new BillProduct();
            BillProductId billProductId = new BillProductId();
            billProductId.setBillId(orderBill.getId());
            billProductId.setProductId(Integer.parseInt(product.get("productId").toString()));
            billProduct.setProductCount(Integer.parseInt(product.get("count").toString()));
            billProduct.setBill(orderBill);
            billProduct.setProduct(productRepository.getById((Integer.parseInt(product.get("productId").toString()))));
            billProduct.setBillProductId(billProductId);
            billProductRepository.save(billProduct);
        }
        return "done";
    }
}
