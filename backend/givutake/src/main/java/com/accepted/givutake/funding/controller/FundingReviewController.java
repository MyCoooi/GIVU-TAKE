package com.accepted.givutake.funding.controller;

import com.accepted.givutake.funding.model.FundingReviewAddDto;
import com.accepted.givutake.funding.model.FundingReviewViewDto;
import com.accepted.givutake.funding.model.FundingReviewUpdateDto;
import com.accepted.givutake.funding.service.FundingReviewService;
import com.accepted.givutake.global.model.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/government-fundings/reviews")
@RequiredArgsConstructor
public class FundingReviewController {

    private final FundingReviewService fundingReviewService;

    // 특정 펀딩의 펀딩 후기 조회
    @GetMapping
    public ResponseEntity<ResponseDto> getFundingReviewListByJwt(@RequestParam int fundingIdx) {
        FundingReviewViewDto savedFundingReviewDto = fundingReviewService.getFundingReviewListByFundingIdx(fundingIdx);

        ResponseDto responseDto = ResponseDto.builder()
                .data(savedFundingReviewDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // jwt 토큰으로 펀딩 후기 추가
    @PostMapping
    public ResponseEntity<ResponseDto> addFundingReviewByJwt(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody FundingReviewAddDto fundingReviewAddDto) {
        String email = userDetails.getUsername();

        FundingReviewViewDto savedFundingReviewDto = fundingReviewService.addFundingReviewByEmail(email, fundingReviewAddDto);

        ResponseDto responseDto = ResponseDto.builder()
                .data(savedFundingReviewDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // jwt 토큰으로 펀딩 후기 수정
    @PatchMapping
    public ResponseEntity<ResponseDto> modifyFundingReviewByJwt(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody FundingReviewUpdateDto fundingReviewUpdateDto) {
        String email = userDetails.getUsername();

        FundingReviewViewDto modifiedFundingReviewDetailViewDto = fundingReviewService.modifyFundingReviewByEmail(email, fundingReviewUpdateDto);

        ResponseDto responseDto = ResponseDto.builder()
                .data(modifiedFundingReviewDetailViewDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
