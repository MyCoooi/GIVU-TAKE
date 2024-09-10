package com.accepted.givutake.gift.controller;

import com.accepted.givutake.gift.entity.GiftReviews;
import com.accepted.givutake.gift.entity.Gifts;
import com.accepted.givutake.gift.model.*;
import com.accepted.givutake.gift.service.GiftService;
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
@RequestMapping("/api/gifts")
@CrossOrigin
public class GiftController {

    private final GiftService giftService;

    @GetMapping // 답례품 조회
    public ResponseEntity<ResponseDto> getGifts(
            @RequestParam(value = "corporationIdx", defaultValue = "")int corporationIdx,
            @RequestParam(value = "search", defaultValue = "")String search,
            @RequestParam(value = "categoryIdx", defaultValue = "")Integer categoryIdx,
            @RequestParam(value = "pageNo", defaultValue = "1")int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        List<GiftDto> gifts = giftService.getGifts(corporationIdx, search, categoryIdx,pageNo, pageSize);
        ResponseDto responseDto = ResponseDto.builder()
                .data(gifts)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{giftIdx}") // 특정 답례품 조회
    public ResponseEntity<ResponseDto> getGift(@PathVariable int giftIdx) {
        Gifts gift = giftService.getGift(giftIdx);
        ResponseDto responseDto = ResponseDto.builder()
                .data(gift)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping // 답례품 생성
    public ResponseEntity<ResponseDto> createGift(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @Valid @RequestBody CreateGiftDto request) {
        giftService.createGift(customUserDetails.getUsername(), request);
        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{giftsIdx}") // 답례품 수정
    public ResponseEntity<ResponseDto> updateGift(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @PathVariable int giftsIdx,
            @Valid @RequestBody UpdateGiftDto request) {
        giftService.updateGift(customUserDetails.getUsername(), giftsIdx, request);
        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{giftsIdx}") // 답례품 삭제
    public ResponseEntity<ResponseDto> deleteGift(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @PathVariable int giftsIdx) {
        giftService.deleteGift(customUserDetails.getUsername(), giftsIdx);
        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{giftIdx}/review") // 특정 답례품에 대한 후기 조회
    public ResponseEntity<ResponseDto> getGiftReviews(
            @PathVariable int giftIdx,
            @RequestParam(value = "pageNo", defaultValue = "1")int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        List<GiftReviews> reviews = giftService.getGiftReviews(giftIdx, pageNo, pageSize);
        ResponseDto responseDto = ResponseDto.builder()
                .data(reviews)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/review") // 특정 사용자가 작성한 후기 조회
    public ResponseEntity<ResponseDto> getUserReviews(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @RequestParam(value = "pageNo", defaultValue = "1")int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10")int pageSize){
        List<GiftReviews> reviews = giftService.getUserReviews(customUserDetails.getUsername(), pageNo, pageSize);
        ResponseDto responseDto = ResponseDto.builder()
                .data(reviews)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/review") // 리뷰 작성
    public ResponseEntity<ResponseDto> createGiftReview(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails ,
            @Valid @RequestBody CreateGiftReviewDto request) {
        giftService.createGiftReview(customUserDetails.getUsername(), request);
        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/review/{reviewIdx}") // 리뷰 수정
    public ResponseEntity<ResponseDto> updateGiftReview(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @PathVariable int reviewIdx,
            @Valid @RequestBody UpdateGiftReviewDto request) {
        giftService.updateGiftReviews(customUserDetails.getUsername(), reviewIdx, request);
        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/review/{reviewIdx}") // 리뷰 삭제
    public ResponseEntity<ResponseDto> deleteGiftReview(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @PathVariable int reviewIdx) {
        giftService.deleteGiftReviews(customUserDetails.getUsername(), reviewIdx);
        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}


