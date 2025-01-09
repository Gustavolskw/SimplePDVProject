package com.web.service.presentation.controller;

import com.web.service.application.dto.ProductCreationDTO;
import com.web.service.application.service.ProductService;
import com.web.service.domain.exception.ListEmptyException;
import com.web.service.domain.model.Product;
import com.web.service.presentation.viewModel.ApiResponse;
import com.web.service.presentation.viewModel.ProductResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${api.prefix}/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@RequestParam("image") MultipartFile image,
                                                     @Valid @RequestPart("product") ProductCreationDTO productCreationDTO) {
        return ResponseEntity.ok().body(new ApiResponse("Produto inserido com sucesso!", new ProductResponseDTO(productService.createProduct(productCreationDTO, image))));

    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts() {
            return ResponseEntity.ok().body(new ApiResponse("Lista de Produtos", productService.findAllProducts().stream().map(ProductResponseDTO::new).toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(new ApiResponse("Produto encontrado", new ProductResponseDTO(productService.findProductById(id))));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchProductsByParam(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "description", required = false) String description) {
        return ResponseEntity.ok().body(new ApiResponse("Lista de Produtos Encontrados", productService.findProductsByName(name).stream().map(ProductResponseDTO::new).toList()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("id") Long id) {
        productService.inactivateProduct(id);
        return ResponseEntity.ok().body(new ApiResponse("Produto Inativado com sucesso", null));
    }
}
