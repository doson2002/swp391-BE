package com.example.swp.controllers;

import com.example.swp.components.PdfGenerator;
import com.example.swp.dtos.ProductDTO;
import com.example.swp.dtos.WarrantyDTO;
import com.example.swp.entities.Orders;
import com.example.swp.entities.Products;
import com.example.swp.entities.Warranty;
import com.example.swp.services.IWarrantyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/warranties")
@RequiredArgsConstructor
public class WarrantyController {

    private final IWarrantyService warrantyService;
    private final PdfGenerator pdfGenerator;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<?> createWarranty(
            @Valid @RequestBody WarrantyDTO warrantyDTO,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Warranty newWarranty = warrantyService.createWarranty(warrantyDTO);

            return ResponseEntity.ok(newWarranty);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/print/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<byte[]> printWarranty(@PathVariable Long id) throws IOException {
        Warranty warranty = warrantyService.getWarrantyById(id);
        ByteArrayInputStream bis = pdfGenerator.createPdf(warranty);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=warranty.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);  // Thiết lập header Content-Type
        return ResponseEntity.ok().headers(headers).body(bis.readAllBytes());
    }


    @GetMapping("/warranties")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<List<Warranty>> getWarrantiesByOrder(@RequestParam Long orderId) {
        Orders order = new Orders();
        order.setId(orderId);
        List<Warranty> warranties = warrantyService.getDetailsByOrder(order);
        return ResponseEntity.ok(warranties);
    }
}
