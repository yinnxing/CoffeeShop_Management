package com.example.project_server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(name = "staff")
public class Staff {
    @Id
    private String id;
    private String name;
    private String email;
    private Date dob;
    private String phoneNumber;
    private String address;
    private Boolean gender;
    private Integer salaryPerDay;
    private String role;
}
