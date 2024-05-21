package com.example.project_client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class BillIngredientCal {
    private String name;
    private Integer count;
    private String unit;
    private Integer total;
    @JsonCreator
    public BillIngredientCal(@JsonProperty("name") String name, @JsonProperty("count") Integer count, @JsonProperty("unit") String unit, @JsonProperty("total") Integer total){
        this.name = name;
        this.count = count;
        this.unit = unit;
        this.total = total;
    }
}
