package com.example.project_client.view.controller.Order.components;

import com.example.project_client.HelloApplication;
import com.example.project_client.model.OrderBill;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class PDFExporter {

    static BaseFont baseFont;
    static final Integer percent = 70;

    static {
        try {
            baseFont = BaseFont.createFont(Objects.requireNonNull(HelloApplication.class.getResource("font/arial.ttf")).toExternalForm(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Font font = new Font(baseFont);


    public static void exportToPDF(OrderBill orderBill, String filePath) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(filePath)));
            document.open();
            addBillContentToPDF(document, orderBill);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }

    private static void addBillContentToPDF(Document document, OrderBill orderBill) throws DocumentException {


        Font titleFont = new Font(baseFont, 30, Font.BOLD);

        Paragraph title = new Paragraph("Hóa đơn", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph(" ", font));
        addTextToPDF(document, "Buy date:", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        addTextToPDF(document, "Customer phone:", orderBill.getCustomerPhoneNumber());
        addTextToPDF(document, "Bill code:", orderBill.getId());
        addTextToPDF(document, "Staff id:", orderBill.getUserStaffId());

        document.add(new Paragraph(" ", font));
        addProductTableToPDF(document, orderBill);

        document.add(new Paragraph(" ", font));
        addTextToPDF(document, "Tổng giá trị hóa đơn:", NumberFormat.getNumberInstance(Locale.US).format(orderBill.getOriginal()) + " VND");
        addTextToPDF(document, "Tổng khấu trừ:", "- " + NumberFormat.getNumberInstance(Locale.US).format(orderBill.getDeduction()) + " VND");
        addTextToPDF(document, "Tổng tiền thanh toán:", NumberFormat.getNumberInstance(Locale.US).format(orderBill.getTotal()) + " VND");
        addTextToPDF(document, "Khách trả (" + (orderBill.getPayMethod() ? "Quét mã QR" : "Tiền mặt") + "):", NumberFormat.getNumberInstance(Locale.US).format(orderBill.getReceived()) + " VND");
        addTextToPDF(document, "Hoàn trả:", NumberFormat.getNumberInstance(Locale.US).format(orderBill.getChangeMoney()) + " VND");
        document.add(new Paragraph(" ", font));
        Paragraph end = new Paragraph("Cảm ơn quý khách!", font);
        end.setAlignment(Element.ALIGN_CENTER);
        document.add(end);

    }

    private static void addTextToPDF(Document document, String label, String value) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(percent);
        float[] columnWidths = {1f, 1f};
        table.setWidths(columnWidths);
        PdfPCell labelCell = new PdfPCell(new Phrase(label, font));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        PdfPCell valueCell = new PdfPCell(new Phrase(value, font));
        valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        valueCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(labelCell);
        table.addCell(valueCell);
        document.add(table);
    }

    private static void addProductTableToPDF(Document document, OrderBill orderBill) throws DocumentException {

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(percent);

        float[] columnWidths = {4f, 2f, 1f, 3f};
        table.setWidths(columnWidths);
        addTableHeader(table, "Tên mặt hàng", "SL", "Đơn giá", "Thành tiền");

        orderBill.getProducts().forEach(product -> addTableHeader(table, product.getName(), product.getCount().toString(), NumberFormat.getNumberInstance(Locale.US).format(product.getPrice()), NumberFormat.getNumberInstance(Locale.US).format(
                (long) product.getCount() * product.getPrice())));
        document.add(new Paragraph(" ", font));
        document.add(table);
    }

    private static void addTableHeader(PdfPTable table, String name, String count, String price, String total) {

        PdfPCell priceCell = new PdfPCell(new Phrase(price, font));
        PdfPCell totalCell = new PdfPCell(new Phrase(total, font));
        PdfPCell nameCell = new PdfPCell(new Phrase(name, font));
        PdfPCell countCell = new PdfPCell(new Phrase(count, font));
        nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        nameCell.setBorder(Rectangle.NO_BORDER);
        countCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        countCell.setBorder(Rectangle.NO_BORDER);
        priceCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        priceCell.setBorder(Rectangle.NO_BORDER);
        totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(nameCell);
        table.addCell(priceCell);
        table.addCell(countCell);
        table.addCell(totalCell);
    }
}
