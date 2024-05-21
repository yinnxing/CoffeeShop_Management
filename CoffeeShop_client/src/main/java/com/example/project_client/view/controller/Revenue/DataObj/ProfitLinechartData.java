package com.example.project_client.view.controller.Revenue.DataObj;
import lombok.Getter;

@Getter
public class ProfitLinechartData {
    public String dateData;
    public Number totalData;
    public Integer salary;
    public Integer billIngre;
    public Integer billPro;

    public ProfitLinechartData(String dateData, Number totalData, Integer salary, Integer billIngre, Integer billPro){
        this.dateData = dateData;
        this.totalData = totalData;
        this.salary = salary;
        this.billIngre = billIngre;
        this.billPro = billPro;
    }
}
