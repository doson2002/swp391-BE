package com.example.swp.services;

import com.example.swp.entities.Counters;
import com.example.swp.entities.Products;
import com.example.swp.entities.TypePrices;
import com.example.swp.repositories.CounterRepository;
import com.example.swp.repositories.TypePricesRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExcelUploadService {

    private final TypePricesRepository typePricesRepository;
    private final CounterRepository counterRepository;

    public static boolean isValidExcelFile(MultipartFile file){
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<Products> getProductsDataFromExcel(InputStream inputStream){
        List<Products> productsList = new ArrayList<>();
        Set<String> uniqueBarcodes = new HashSet<>(); // Set để theo dõi các mã code đã xuất hiện
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("products");
            int rowIndex =0;
            for (Row row : sheet){
                if (rowIndex ==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Products product = new Products();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cellIndex){

                        case 1 -> product.setBarcode(cell.getStringCellValue());
                        case 2 -> product.setProductName(cell.getStringCellValue());
                        case 3 -> product.setQuantity((int) cell.getNumericCellValue());
                        case 4 -> product.setPriceProcessing((long) cell.getNumericCellValue());
                        case 5 -> product.setPriceStone((long) cell.getNumericCellValue());
                        case 6 -> product.setWeight(cell.getNumericCellValue());
                        case 7 -> product.setWeightUnit(cell.getStringCellValue());
                        case 8 -> product.setDescription(cell.getStringCellValue());
                        case 9 -> product.setStatus( cell.getBooleanCellValue());
                        case 10 -> product.setPriceRate(cell.getNumericCellValue());
                        case 11 -> product.setImageUrl(cell.getStringCellValue());
                        case 12 -> {
                            // Assuming cell contains some identifier for the trainingUnit
                            long typeId = (long) cell.getNumericCellValue();
                            // Assuming you have a method to retrieve trainingUnit by its ID
                            TypePrices typePrice = typePricesRepository.findById(typeId)
                                    .orElseThrow(() -> new RuntimeException("Type price not found with id: " + typeId));
                            product.setType(typePrice);
                        }
                        case 13 -> {
                            // Assuming cell contains some identifier for the trainingUnit
                            long counterId = (long) cell.getNumericCellValue();
                            // Assuming you have a method to retrieve trainingUnit by its ID
                            Counters counter = counterRepository.findById(counterId)
                                    .orElseThrow(() -> new RuntimeException("Counter not found with id: " + counterId));
                            product.setCounter(counter);
                        }

                    }
                    cellIndex++;
                }
                if (!uniqueBarcodes.contains(product.getBarcode())) { // Kiểm tra xem mã code đã tồn tại chưa
                    productsList.add(product); // Chỉ thêm vào danh sách nếu mã code chưa tồn tại
                    uniqueBarcodes.add(product.getBarcode()); // Thêm mã code vào Set để kiểm tra lần sau
                }

            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return productsList;
    }
}

