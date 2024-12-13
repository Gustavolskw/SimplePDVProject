package com.web.service.presentation.controller;

import com.web.service.application.dto.ProductCreationDTO;
import com.web.service.application.service.ProductService;
import com.web.service.domain.exception.ListEmptyException;
import com.web.service.domain.model.Product;
import com.web.service.presentation.viewModel.ApiResponse;
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
        try{
            return ResponseEntity.ok().body(new ApiResponse("Produto inserido com sucesso!", productService.createProduct(productCreationDTO, image)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts() {
        try{
        return ResponseEntity.ok().body(new ApiResponse("Lista de Produtos", productService.findAllProducts()));
        }catch (Exception e) {
            if (e instanceof ListEmptyException) return ResponseEntity.status(200).body(new ApiResponse(e.getMessage(), null));
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }
}
