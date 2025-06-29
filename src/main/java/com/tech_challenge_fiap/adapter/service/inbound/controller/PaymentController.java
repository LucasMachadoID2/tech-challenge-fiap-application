package com.tech_challenge_fiap.adapter.service.inbound.controller;

import com.tech_challenge_fiap.adapter.service.inbound.dto.PaymentRequestDto;
import com.tech_challenge_fiap.core.domain.payment.PaymentUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tech_challenge_fiap.util.converter.OrderConverter.toResponse;

@RestController
@RequestMapping("/v1/payments")
@AllArgsConstructor
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    @PatchMapping
    public ResponseEntity<?> create(@RequestBody PaymentRequestDto paymentRequestDto) {
        var updatedOrder = paymentUseCase.updatePaymentStatus(paymentRequestDto);
        return ResponseEntity.ok(toResponse(updatedOrder));
    }
}
