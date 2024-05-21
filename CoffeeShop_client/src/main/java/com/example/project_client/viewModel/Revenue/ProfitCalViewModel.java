package com.example.project_client.viewModel.Revenue;

import lombok.Getter;

@Getter
public class ProfitCalViewModel {
    private final BillProductViewModel billProductViewModel = new BillProductViewModel();
    private final SalaryCalViewModel salaryCalViewModel = new SalaryCalViewModel();
}
