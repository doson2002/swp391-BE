package com.example.swp.services;

import com.example.swp.dtos.ProductDTO;
import com.example.swp.dtos.TypePriceDTO;
import com.example.swp.entities.Counters;
import com.example.swp.entities.Products;
import com.example.swp.entities.TypePrices;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.repositories.CounterRepository;
import com.example.swp.repositories.ProductRepository;
import com.example.swp.repositories.TypePricesRepository;
import com.example.swp.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService{
    private final CounterRepository counterRepository;
    private final ProductRepository productRepository;
    private final TypePricesRepository typePricesRepository;
    public Products createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Counters existingCounter = counterRepository.findById(productDTO.getCounterId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find counter id"+ productDTO.getCounterId()));
        TypePrices existingType = typePricesRepository.findById(productDTO.getTypeId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find type with id"+productDTO.getTypeId()));
        Products newProduct = Products
                .builder()
                .productName(productDTO.getProductName())
                .barcode(productDTO.getBarcode())
                .priceProcessing(productDTO.getPriceProcessing())
                .priceStone(productDTO.getPriceStone())
                .weight(productDTO.getWeight())
                .quantity(productDTO.getQuantity())
                .description(productDTO.getDescription())
                .type(existingType)
                .counter(existingCounter)
                .build();
        return productRepository.save(newProduct);
    }
    @Override
    public Products updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException {
        Products existingProduct = productRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Product cannot find with id"+ id));
        if(existingProduct!=null){
            Counters existingCounter =
                    counterRepository.findById(productDTO.getCounterId())
                            .orElseThrow(()->
                                    new DataNotFoundException("cannot find counter with id"+ productDTO.getCounterId()));
            TypePrices existingType = typePricesRepository.findById(productDTO.getTypeId())
                    .orElseThrow(()-> new DataNotFoundException("Cannot find type with id"+productDTO.getTypeId()));
            existingProduct.setProductName(productDTO.getProductName());
            existingProduct.setBarcode(productDTO.getBarcode());
            existingProduct.setPriceProcessing(productDTO.getPriceProcessing());
            existingProduct.setPriceStone(productDTO.getPriceStone());
            existingProduct.setType(existingType);
            existingProduct.setWeight(productDTO.getWeight());
            existingProduct.setQuantity(productDTO.getQuantity());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setCounter(existingCounter);
            return productRepository.save(existingProduct);
        }
        return null;
    }
    public void blockOrEnable(Long id, Boolean active) throws DataNotFoundException {
        Products existingProduct = productRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("product not found"));
        existingProduct.setStatus(active);
        productRepository.save(existingProduct);
    }
    public Page<ProductResponse>getAllProducts(String keyword, PageRequest pageRequest) {
        Page<Products> productPage = productRepository.searchProducts(keyword, pageRequest);
        return productPage.map(ProductResponse::fromProducts);
    }


}
