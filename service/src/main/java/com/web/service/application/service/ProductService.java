package com.web.service.application.service;

import com.web.service.application.dto.ProductCreationDTO;
import com.web.service.domain.exception.ListEmptyException;
import com.web.service.domain.model.Product;
import com.web.service.domain.repository.ProductRepository;
import com.web.service.presentation.viewModel.ProductResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {

        private final ProductRepository productRepository;
        private final ImageService imageService;



    public ProductResponseDTO createProduct(ProductCreationDTO product, MultipartFile image) {
        Product savedProduct = new Product();
        savedProduct.setName(product.name());
        savedProduct.setDescription(product.description());
        savedProduct.setValue(product.value());
        try{
            String imageName = imageService.upload(image);
            savedProduct.setImageUrl(imageName);
        }catch (Exception e){
            log.error(e.getMessage());
            savedProduct.setImageUrl(null);
        }
        productRepository.save(savedProduct);
        return new ProductResponseDTO(savedProduct);
    }


    public List<ProductResponseDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();
        if(products.isEmpty())throw new ListEmptyException("Product list is empty");
        return products.stream().map(ProductResponseDTO::new).toList();
    }
}
