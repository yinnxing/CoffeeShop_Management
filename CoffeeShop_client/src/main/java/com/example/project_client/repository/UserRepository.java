package com.example.project_client.repository;

import com.example.project_client.data.Api;
import com.example.project_client.data.JsonUtils;
import com.example.project_client.data.Request;
import com.example.project_client.model.User;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserRepository {


    // Retrieves a user with the given username
    public User getUser(String username)  {
        User user = new User();
        try {
             return user = JsonUtils.fromJson(Request.sendGetRequest(Api.userApi + "?username=" + username), User.class);
        } catch (Exception e){
            System.out.println("error: "+e);
        }
        return null;
    }


    // Retrieves a list of all users
    public List<User> getAllUsers() {
        try {
            return JsonUtils.fromJson(Request.sendGetRequest(Api.userApi + "/getAll"), new TypeReference<>() {
            });
        } catch (Exception e){
            System.out.println("error: "+e);
        }
        return null;
    }
    public List<User> getByUserName(String userName) throws IOException {
        return JsonUtils.fromJson(Request.sendGetRequest(Api.userApi + "/getByUsername/" + userName), new TypeReference<>() {
        });
    }
    public List<User> getBySearch(String stringSearch) throws  IOException{
        return JsonUtils.fromJson(Request.sendGetRequest(Api.userApi + "/getBySearch/" + stringSearch), new TypeReference<>() {
        });
    }
    public void addUser(User user) throws Exception {
        Request.sendPostRequest(Api.userApi + "/addUser", Objects.requireNonNull(JsonUtils.toJson(user)));
    }
    public void updateUser(User user) throws Exception {
        Request.sendPutRequest(Api.userApi + "/updateUser", Objects.requireNonNull(JsonUtils.toJson(user)));
    }
    public void deleteUser(Integer id) throws IOException {
        Request.sendDeleteRequest(Api.userApi + "/deleteUser/" + id);
    }
    public List<User> getByStaffId(String staffId) throws IOException {
        return JsonUtils.fromJson(Request.sendGetRequest(Api.userApi + "/getByStaffId/" + staffId), new TypeReference<>() {
        });
    }
}


