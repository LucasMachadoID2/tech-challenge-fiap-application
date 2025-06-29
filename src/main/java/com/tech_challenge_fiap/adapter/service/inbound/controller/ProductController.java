package com.tech_challenge_fiap.adapter.service.inbound.controller;

import com.tech_challenge_fiap.adapter.service.inbound.dto.ProductRequestDto;
import com.tech_challenge_fiap.adapter.service.inbound.dto.ProductResponseDto;
import com.tech_challenge_fiap.core.domain.product.ProductUseCase;
import com.tech_challenge_fiap.util.Enum.CategoryEnum;
import com.tech_challenge_fiap.util.converter.ProductConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.tech_challenge_fiap.util.converter.ProductConverter.*;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;

    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@RequestBody ProductRequestDto request) {
        var created = productUseCase.create(toDomain(request));
        return ResponseEntity.ok(toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(@PathVariable String id, @RequestBody ProductRequestDto request) {
        var updated = productUseCase.update(id, toDomain(request));
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        productUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> list() {
        var products = productUseCase.findAll().stream()
                .map(ProductConverter::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductResponseDto>> findByCategory(@RequestParam(value = "category") CategoryEnum category) {
        var products = productUseCase.findByCategory(category).stream()
                .map(ProductConverter::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }
}
