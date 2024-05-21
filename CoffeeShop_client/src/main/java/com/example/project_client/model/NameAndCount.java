package com.example.project_client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class NameAndCount {
    private String name;
    private Integer count;

    @JsonCreator
    public NameAndCount(@JsonProperty("name") String name, @JsonProperty("count") Integer count){
        this.name = name;
        this.count = count;
    }
}
