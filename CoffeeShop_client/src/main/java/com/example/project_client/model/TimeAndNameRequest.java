package com.example.project_client.model;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TimeAndNameRequest {
    public String stringSearch;
    public LocalDate dateStart;

    public LocalDate dateEnd;

    public TimeAndNameRequest(){

    }
    public TimeAndNameRequest(String stringSearch, LocalDate dateStart, LocalDate dateEnd) {
        this.stringSearch = stringSearch;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public void setStringSearch(String stringSearch) {
        this.stringSearch = stringSearch;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }
}
