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

    @GetMapping
    public ResponseEntity<ResponseDto> getParticipants(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(value = "pageNo", defaultValue = "1")int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        List<ParticipantDto> participants = participantService.getParticipants(userDetails.getUsername(), pageNo, pageSize);
        ResponseDto responseDto = ResponseDto.builder()
                .data(participants)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

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
            return readyResponse;
        }else{
            return ReadyResponse.builder()
                    .status("success")
                    .build();
        }
    }
}
