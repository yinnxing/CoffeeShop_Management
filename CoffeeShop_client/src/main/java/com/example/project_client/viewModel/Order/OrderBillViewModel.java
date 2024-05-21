package com.example.project_client.viewModel.Order;

import com.example.project_client.event.Data;
import com.example.project_client.model.OrderBill;
import com.example.project_client.repository.OrderBillRepository;

import com.example.project_client.view.controller.Order.components.PDFExporter;
import javafx.stage.FileChooser;
import lombok.Getter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderBillViewModel {
    private final OrderBillRepository orderBillRepository = new OrderBillRepository();
    @Getter
    private OrderBill data;
    public void initData(OrderBill orderBill, Boolean isCreate) throws Exception {
        this.data = orderBill;
        if(isCreate) {

            data.setId("OB" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("ssmmHHddMMyyyy")));
            data.setUserStaffId(Data.getUser().getStaffId());
            data.setChangeMoney(data.getReceived() - data.getTotal());
            orderBillRepository.saveOrderBillApi(data);
          //  export(orderBill);
        }
    }
    private void export(OrderBill orderBill){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Bill", "*.pdf"));

        fileChooser.setInitialDirectory(new File(Data.getBillPath()));
        fileChooser.setInitialFileName(orderBill.getId());
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (FileOutputStream ignored = new FileOutputStream(file)) {
                PDFExporter.exportToPDF(orderBill,file.getAbsolutePath());
                System.out.println("PDF saved successfully to: " + file.getAbsolutePath());
            } catch (IOException  e) {
                e.printStackTrace();
            }
        }
    }
}
