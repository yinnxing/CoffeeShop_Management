package com.example.project_client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class Staff {
    private String id;
    private String name;
    private String email;
    private LocalDate dob;
    private String phoneNumber;
    private String address;
    private String gender;
    private Integer salaryPerDay;
    private String role;


    @JsonCreator
    public Staff(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("email") String email,
                 @JsonProperty("dob") LocalDate dob, @JsonProperty("phoneNumber") String phoneNumber,
                 @JsonProperty("address") String address, @JsonProperty("gender") String gender, @JsonProperty("salaryPerDay") Integer salaryPerDay,
                 @JsonProperty("role") String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.salaryPerDay = salaryPerDay;
        this.role = role;
    }

    public Staff() {
            this.id = null;
            this.name = null;
            this.email = null;
            this.dob = null;
            this.phoneNumber = null;
            this.address = null;
            this.gender = null;
            this.salaryPerDay = null;
            this.role = null;

    }
}

