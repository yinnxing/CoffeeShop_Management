package com.example.project_client.viewModel.Revenue;

import com.example.project_client.model.NameAndCount;
import com.example.project_client.model.SalaryCal;
import com.example.project_client.model.TimeAndNameRequest;
import com.example.project_client.model.TimeRequest;
import com.example.project_client.repository.SalaryCalRepository;

import java.util.List;

public class SalaryCalViewModel {
    private  final SalaryCalRepository salaryCalRepo = new SalaryCalRepository();

    public List<SalaryCal> getAllSalary(TimeRequest timeRequest) throws Exception {
        return salaryCalRepo.getAllSalaryApi(timeRequest);
    }
    public List<SalaryCal> getBySearching(TimeAndNameRequest timeAndNameRequest) throws Exception {
        return salaryCalRepo.getBySearchingApi(timeAndNameRequest);
    }
    public List<NameAndCount> getPerDay(TimeRequest timeRequest) throws Exception {
        return salaryCalRepo.getPerDayApi(timeRequest);
    }
    public List<NameAndCount> getPerMonth(TimeRequest timeRequest) throws Exception {
        return salaryCalRepo.getPerMonthApi(timeRequest);
    }
}
