package com.accepted.givutake.payment.controller;

import com.accepted.givutake.global.model.ResponseDto;
import com.accepted.givutake.payment.entity.FundingParticipants;
import com.accepted.givutake.payment.model.CreateParticipateDto;
import com.accepted.givutake.payment.model.ParticipantDto;
import com.accepted.givutake.payment.model.ReadyResponse;
import com.accepted.givutake.payment.service.KaKaoPayService;
import com.accepted.givutake.payment.service.ParticipantService;
import com.accepted.givutake.payment.utils.SessionUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/participants")
@CrossOrigin
@Slf4j
public class ParticipantController {

    private final ParticipantService participantService;
    private final KaKaoPayService kaKaoPayService;

    @PostMapping
    public @ResponseBody ReadyResponse createParticipant(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateParticipateDto request
    ) {
        FundingParticipants participants = participantService.createParticipants(userDetails.getUsername(), request);
        if(request.getPaymentMethod().equals("KAKAO")){
            ReadyResponse readyResponse = kaKaoPayService.payFundingReady(userDetails.getUsername(), participants.getParticipantIdx(), request);
            SessionUtils.addAttribute("tid", readyResponse.getTid());
            readyResponse.setStatus("success");
            participantService.updateFunding(request.getFundingIdx(), request.getPrice());
            return readyResponse;
        }else{
            participantService.updateFunding(request.getFundingIdx(), request.getPrice());
            return ReadyResponse.builder()
                    .status("success")
                    .build();
        }
    }
}
