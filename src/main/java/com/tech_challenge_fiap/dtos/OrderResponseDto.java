package com.tech_challenge_fiap.dtos;

import com.tech_challenge_fiap.adapter.service.inbound.dto.ClientResponseDto;
import com.tech_challenge_fiap.adapter.service.inbound.dto.PaymentResponseDto;
import com.tech_challenge_fiap.adapter.service.inbound.dto.ProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class OrderResponseDto {
    
    private String id;
    
    private String status;
    
    private ClientResponseDto client;
    
    private List<ProductResponseDto> products;

    private PaymentResponseDto payment;
}
