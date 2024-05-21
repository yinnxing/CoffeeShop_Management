package com.example.project_client.repository;

import com.example.project_client.data.Api;
import com.example.project_client.data.JsonUtils;
import com.example.project_client.data.Request;
import com.example.project_client.model.NameAndCount;
import com.example.project_client.model.SalaryCal;
import com.example.project_client.model.TimeAndNameRequest;
import com.example.project_client.model.TimeRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.Objects;

public class SalaryCalRepository {
    public List<SalaryCal> getAllSalaryApi(TimeRequest timeRequest) throws Exception {
        return JsonUtils.fromJson(Request.sendPostRequestGettingData(Api.salaryCalApi + "/getAll", Objects.requireNonNull(JsonUtils.toJson(timeRequest))), new TypeReference<>() {
        });
    }

    public List<SalaryCal> getBySearchingApi(TimeAndNameRequest timeAndNameRequest) throws Exception {
        return JsonUtils.fromJson(Request.sendPostRequestGettingData(Api.salaryCalApi + "/getBySearching", Objects.requireNonNull(JsonUtils.toJson(timeAndNameRequest))), new TypeReference<>() {
        });
    }

    public List<NameAndCount> getPerDayApi(TimeRequest timeRequest) throws Exception {
        return JsonUtils.fromJson(Request.sendPostRequestGettingData(Api.salaryCalApi + "/getPerDay", Objects.requireNonNull(JsonUtils.toJson(timeRequest))), new TypeReference<>() {
        });
    }

    public List<NameAndCount> getPerMonthApi(TimeRequest timeRequest) throws Exception {
        return JsonUtils.fromJson(Request.sendPostRequestGettingData(Api.salaryCalApi + "/getPerMonth", Objects.requireNonNull(JsonUtils.toJson(timeRequest))), new TypeReference<>() {
        });
    }
}
