package com.example.project_client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class SalaryCal {
    private String id;
    private String name;
    private Integer salary;

    @JsonCreator
    public SalaryCal(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("salary") Integer salary){
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

}
