package com.web.service.application.service;

import com.web.service.application.dto.ProductCreationDTO;
import com.web.service.domain.exception.AlreadyExistsException;
import com.web.service.domain.exception.EntityNotFoundException;
import com.web.service.domain.exception.ListEmptyException;
import com.web.service.domain.model.Product;
import com.web.service.domain.repository.ProductRepository;
import com.web.service.domain.validation.ProductValidation;
import com.web.service.presentation.viewModel.ProductResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ImageService imageService;
    private final ProductValidation productValidation;
    private final ProductTypeService productTypeService;

    public Product createProduct(ProductCreationDTO product, MultipartFile image) {
        productValidation.validateName(product.name());
        productValidation.validateNameInsert(product.name());
        productValidation.validateDescription(product.description());
        productValidation.validatePrice(product.value());
        productValidation.validateTypeExistence(product.typeId());
        Product savedProduct = buildProductFromDTO(product, image);
        productRepository.save(savedProduct);
        return savedProduct;
    }

    public List<Product> findAllProducts() {
        List<Product> products = productRepository.findAll();
        if(products.isEmpty())throw new ListEmptyException("Product list is empty");
        return products;
    }

    public Product findProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty())throw new EntityNotFoundException("Product not found");
        return product.get();
    }

    public List<Product> findProductsByName(String name) {
        Optional<List<Product>> listProductsSerach = productRepository.searchProductsByParamName("%"+name+"%");
        if(listProductsSerach.isPresent()){
            if(listProductsSerach.get().isEmpty())throw new ListEmptyException("Product list is empty");
            return listProductsSerach.get();
        }
        throw new ListEmptyException("Product list is empty");
    }


    public void inactivateProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        product.setStatus(false);
        productRepository.save(product);
    }

    public Product updateProduct(Long prodtId, ProductCreationDTO product, MultipartFile image) {
        Product oldProduct = findProductById(prodtId);
        if(product!=null){
        if(!product.name().isEmpty() && !product.name().isBlank()){
            productValidation.validateName(product.name());
            oldProduct.setName(product.name());
        }
        if(!product.description().isEmpty() && !product.description().isBlank()){
            productValidation.validateDescription(product.description());
            oldProduct.setDescription(product.description());
        }
        if(product.value() != null ){
            productValidation.validatePrice(product.value());
            oldProduct.setValue(product.value());
        }
        if(product.typeId()!= null){
            productValidation.validateTypeExistence(product.typeId());
            oldProduct.setProductType(productTypeService.getProductTypeById(product.typeId()));
        }
        }
        if(image!= null && !image.isEmpty()){
            try{
                oldProduct.setImageUrl(imageService.updateImage(image, oldProduct.getImageUrl()));
            }catch (Exception e){
                log.error(e.getMessage());
                oldProduct.setImageUrl(null);
            }
        }

        return oldProduct;
    }


    private Product buildProductFromDTO(ProductCreationDTO productCreationDTO, MultipartFile image) {
        Product product = new Product();
        product.setName(productCreationDTO.name());
        product.setDescription(productCreationDTO.description());
        product.setValue(productCreationDTO.value());
        product.setProductType(productTypeService.getProductTypeById(productCreationDTO.typeId()));
        try{
            String imageName = imageService.upload(image);
            product.setImageUrl(imageName);
        }catch (Exception e){
            log.error(e.getMessage());
            product.setImageUrl(null);
        }
        product.setStatus(true);
        return product;
    }

}
