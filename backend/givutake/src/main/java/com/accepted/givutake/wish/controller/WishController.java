package com.accepted.givutake.wish.controller;

import com.accepted.givutake.global.model.ResponseDto;
import com.accepted.givutake.user.common.model.CustomUserDetailsDto;
import com.accepted.givutake.wish.entity.Wish;
import com.accepted.givutake.wish.model.WishDto;
import com.accepted.givutake.wish.service.WishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/wish")
@CrossOrigin
public class WishController {

    private final WishService wishService;

    @GetMapping
    public ResponseEntity<ResponseDto> getWish(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @RequestParam(value = "pageNo", defaultValue = "1")int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        List<Wish> wishList = wishService.getWishList(customUserDetails.getUsername(), pageNo, pageSize);
        ResponseDto responseDto = ResponseDto.builder()
                .data(wishList)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createWish(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @Valid @RequestBody WishDto request) {
        wishService.createWish(customUserDetails.getUsername(), request);
        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteWish(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @RequestParam(value = "wishIdx") int wishIdx) {
        wishService.deleteWish(customUserDetails.getUsername(), wishIdx);
        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{giftIdx}")
    public ResponseEntity<ResponseDto> getIsWish(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @PathVariable int giftIdx){
        boolean isWish = wishService.isWish(customUserDetails.getUsername(), giftIdx);
        ResponseDto responseDto = ResponseDto.builder()
                .data(isWish)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
