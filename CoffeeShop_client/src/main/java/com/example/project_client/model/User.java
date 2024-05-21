package com.example.project_client.model;

import lombok.Getter;

    @Getter
    public class User {
        private Integer id;
        private String username;
        private String password;
        private String staffId;
        public User(Integer id, String username, String password, String staffId){
            this.id = id;
            this.username = username;
            this.password = password;
            this.staffId = staffId;
        }
        public User(){

        }
    }
