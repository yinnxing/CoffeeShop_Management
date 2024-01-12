package com.example.project_server.service;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Map;

@Component
public interface OrderBillService {
    String saveBill(Map<String, Object> bill) throws ParseException;
}
