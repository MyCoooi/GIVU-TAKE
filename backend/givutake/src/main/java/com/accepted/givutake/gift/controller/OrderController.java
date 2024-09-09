package com.accepted.givutake.gift.controller;

import com.accepted.givutake.gift.entity.Orders;
import com.accepted.givutake.gift.model.CreateOrderDto;
import com.accepted.givutake.gift.model.UpdateOrderDto;
import com.accepted.givutake.gift.service.OrderService;
import com.accepted.givutake.global.model.ResponseDto;
import com.accepted.givutake.user.common.model.CustomUserDetailsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchases")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ResponseDto> getOrders(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @RequestParam(value = "pageNo", defaultValue = "1")int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        List<Orders> orders = orderService.getOrdres(customUserDetails.getUsername(), pageNo, pageSize);
        ResponseDto responseDto = ResponseDto.builder()
                .data(orders)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{orderIdx}")
    public ResponseEntity<ResponseDto> getOrder(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @PathVariable int orderIdx){
        Orders order = orderService.getOrder(customUserDetails.getUsername(), orderIdx);
        ResponseDto responseDto = ResponseDto.builder()
                .data(order)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createOrder(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @Valid @RequestBody CreateOrderDto request) {
        orderService.createOrder(customUserDetails.getUsername(), request);
        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{orderIdx}")
    public ResponseEntity<ResponseDto> updateOrder(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @PathVariable int orderIdx,
            @Valid @RequestBody UpdateOrderDto request){
        orderService.updateOrder(customUserDetails.getUsername(), orderIdx, request);
        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
