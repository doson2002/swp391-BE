package com.example.swp.components;

import com.example.swp.dtos.WarrantyDTO;
import com.example.swp.entities.Orders;
import com.example.swp.entities.Warranty;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.repositories.OrderRepository;
import com.example.swp.repositories.TokenRepository;
import com.example.swp.repositories.WarrantyRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
@Component
@RequiredArgsConstructor
public class PdfGenerator {
    private final OrderRepository orderRepository;
    public ByteArrayInputStream createPdf(Warranty warranty) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Orders existingOrder = orderRepository.findById(warranty.getOrder().getId())
                    .orElseThrow(()->new DataNotFoundException("Order not found"));
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph("Warranty Details"));
            document.add(new Paragraph("Customer Name: " + warranty.getCustomerName()));
            document.add(new Paragraph("Created date: " + warranty.getCreatedDate()));
            document.add(new Paragraph("Warranty Time: " + warranty.getTimeWarranty()));
            document.add(new Paragraph("Warranty Details: " + warranty.getWarrantyDetail()));
            document.add(new Paragraph("Order Id: " + warranty.getOrder().getId()));
            document.close();
        } catch (DocumentException | DataNotFoundException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}

