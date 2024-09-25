package com.accepted.givutake.gift.controller;

import com.accepted.givutake.gift.model.CreateOrderDto;
import com.accepted.givutake.gift.model.OrderDto;
import com.accepted.givutake.gift.model.UpdateOrderDto;
import com.accepted.givutake.gift.service.OrderService;
import com.accepted.givutake.global.model.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(value = "pageNo", defaultValue = "1")int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        List<OrderDto> orders = orderService.getOrdres(userDetails.getUsername(), pageNo, pageSize);
        ResponseDto responseDto = ResponseDto.builder()
                .data(orders)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{orderIdx}")
    public ResponseEntity<ResponseDto> getOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable int orderIdx){
        OrderDto order = orderService.getOrder(userDetails.getUsername(), orderIdx);
        ResponseDto responseDto = ResponseDto.builder()
                .data(order)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateOrderDto request) {
        orderService.createOrder(userDetails.getUsername(), request);
        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{orderIdx}")
    public ResponseEntity<ResponseDto> updateOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable int orderIdx,
            @Valid @RequestBody UpdateOrderDto request){
        orderService.updateOrder(userDetails.getUsername(), orderIdx, request);
        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/statistics/{giftIdx}")
    public ResponseEntity<ResponseDto> getOrderStatistics(@PathVariable int giftIdx){
        int data = orderService.countGift(giftIdx);
        ResponseDto responseDto = ResponseDto.builder()
                .data(data)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
