package com.tech_challenge_fiap.adapter.service.inbound.controller;

import com.tech_challenge_fiap.adapter.service.inbound.dto.OrderRequestDto;
import com.tech_challenge_fiap.adapter.service.inbound.dto.OrderResponseDto;
import com.tech_challenge_fiap.core.domain.order.Order;
import com.tech_challenge_fiap.core.domain.order.OrderStatusEnum;
import com.tech_challenge_fiap.core.domain.order.OrderUseCase;
import com.tech_challenge_fiap.util.converter.OrderConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tech_challenge_fiap.util.converter.OrderConverter.toResponse;

@RestController
@RequestMapping("/v1/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderUseCase orderUseCase;

    @PostMapping
    public ResponseEntity<OrderResponseDto> create(@RequestBody OrderRequestDto orderRequestDTO) {
        var savedOrder = orderUseCase.createOrder(orderRequestDTO);
        return ResponseEntity.ok(toResponse(savedOrder));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> list() {
        List<Order> orders = orderUseCase.findAll();
        return ResponseEntity.ok(orders.stream().map(OrderConverter::toResponse).toList());
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> updateStatus(@PathVariable String orderId,
                                                         @RequestParam OrderStatusEnum status) {
        var updatedOrder = orderUseCase.updateStatus(orderId, status);
        return ResponseEntity.ok(toResponse(updatedOrder));
    }
}
