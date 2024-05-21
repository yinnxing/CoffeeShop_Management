package com.example.project_client.repository;

import com.example.project_client.data.Api;
import com.example.project_client.data.JsonUtils;
import com.example.project_client.data.Request;
import com.example.project_client.model.OrderBill;
import com.fasterxml.jackson.core.type.TypeReference;


import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class OrderBillRepository {
    public void saveOrderBillApi(Object data) throws Exception {
        Request.sendPutRequest(Api.orderBillApi + "/saveBill", Objects.requireNonNull(JsonUtils.toJson(data)));
    }

    public List<OrderBill> getAll() throws IOException {
        return JsonUtils.fromJson(Request.sendGetRequest(Api.orderBillApi + "/getAll"), new TypeReference<>() {
        });
    }

}
