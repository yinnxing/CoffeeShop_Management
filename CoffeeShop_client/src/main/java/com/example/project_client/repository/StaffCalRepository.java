package com.example.project_client.repository;

import com.example.project_client.data.Api;
import com.example.project_client.data.JsonUtils;
import com.example.project_client.data.Request;
import com.example.project_client.model.Staff;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public  class StaffCalRepository {
    public static void saveStaffApi(Staff staff) throws Exception {
        Request.sendPostRequest(Api.staffApi+"/save", Objects.requireNonNull(JsonUtils.toJson(staff))) ;
    }

    public  List<Staff> getAllStaffApi() throws IOException {
        String jsonObject = Request.sendGetRequest(Api.staffApi+"/getAll");
        System.out.println(jsonObject);
        return JsonUtils.fromJson(jsonObject, new TypeReference<>() {
        });
    }

    public  void updateStaffApi(Staff staff) throws Exception {
        Request.sendPutRequest(Api.staffApi + "/update/"+staff.getId(), Objects.requireNonNull(JsonUtils.toJson(staff)));
    }

    public  void deleteStaffApi(String id) throws IOException {
        Request.sendDeleteRequest(Api.staffApi + "/delete/" + id);
    }

    public Staff getStaffByIdApi(String id) throws Exception {
        String jsonObject = Request.sendGetRequest(Api.staffApi+"/getById/" + id);
        System.out.println(jsonObject);
        return JsonUtils.fromJson(jsonObject, Staff.class);
    }
}
