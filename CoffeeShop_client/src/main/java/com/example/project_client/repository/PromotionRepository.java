package com.example.project_client.repository;

import com.example.project_client.data.Api;
import com.example.project_client.data.JsonUtils;
import com.example.project_client.data.Request;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PromotionRepository {

    public void savePromotion(Map<String, Object> promotion) throws Exception {
        Request.sendPutRequest(Api.promotionApi + "/save", Objects.requireNonNull(JsonUtils.toJson(promotion)));
    }

    public List<Map<String, Object>> getAllPromotion() throws IOException {
        return JsonUtils.fromJson(Request.sendGetRequest(Api.promotionApi + "/getPromotions"), new TypeReference<>() {
        });
    }

    public Map<String, Object> getPromotionByDate(LocalDate date) throws IOException {
        return JsonUtils.fromJson(Request.sendGetRequest(Api.promotionApi + "/getPromotion?date=" + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), new TypeReference<>() {
        });
    }

    public Boolean checkPromotion(LocalDate start, LocalDate end, Integer id) throws IOException {
        return JsonUtils.fromJson(Request.sendGetRequest(Api.promotionApi + "/check/" + id + "?startDate=" + start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "&endDate=" + end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), Boolean.class);
    }

    public void removePromotion(Integer id) throws IOException {
        Request.sendDeleteRequest(Api.promotionApi + "/delete/" + id);
    }
}
