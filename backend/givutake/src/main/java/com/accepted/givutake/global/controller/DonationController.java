package com.accepted.givutake.global.controller;

import com.accepted.givutake.funding.service.FundingParticipantService;
import com.accepted.givutake.global.model.ResponseDto;
import com.accepted.givutake.payment.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/donations")
@Slf4j
public class DonationController {

    private final OrderService orderService;
    private final FundingParticipantService fundingParticipantService;

    @GetMapping("/price")
    public ResponseEntity<ResponseDto> calculateTotalPrice() {
        long fundingSum = fundingParticipantService.calculateTotalFundingFee();
        long giftSum = orderService.calculateTotalOrderPrice();

        long sum = fundingSum + giftSum;

        Map<String, Long> map = new HashMap<>();
        map.put("price", sum);

        ResponseDto responseDto = ResponseDto.builder()
                .data(map)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
