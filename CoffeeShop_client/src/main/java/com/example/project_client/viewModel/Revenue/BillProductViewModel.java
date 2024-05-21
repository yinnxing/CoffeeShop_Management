package com.example.project_client.viewModel.Revenue;

import com.example.project_client.model.NameAndCount;
import com.example.project_client.model.TimeRequest;
import com.example.project_client.repository.BillProductCalRepository;

import java.util.List;

public class BillProductViewModel {
    private final BillProductCalRepository billProductCalRepo = new BillProductCalRepository();

    public List<NameAndCount> getCount(TimeRequest timeRequest) throws  Exception{
        return billProductCalRepo.getCountApi(timeRequest);
    }
    public List<NameAndCount> getPerDay(TimeRequest timeRequest) throws Exception{
        return billProductCalRepo.getPerDayApi(timeRequest);
    }
    public List<NameAndCount> getPerMonth(TimeRequest timeRequest) throws Exception{
        return billProductCalRepo.getPerMonthApi(timeRequest);
    }
}
