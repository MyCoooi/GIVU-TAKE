package com.accepted.givutake.cart.controller;

import com.accepted.givutake.cart.entity.Carts;
import com.accepted.givutake.cart.model.CreateCartDto;
import com.accepted.givutake.cart.model.UpdateCartDto;
import com.accepted.givutake.cart.service.CartService;
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
@RequestMapping("/api/users/shopping-cart")
@CrossOrigin
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ResponseDto> getCart(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @RequestParam(value = "pageNo", defaultValue = "1")int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        List<Carts> cartsList = cartService.getCartList(customUserDetails.getUsername(), pageNo, pageSize);
        ResponseDto responseDto = ResponseDto.builder()
                .data(cartsList)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createCart(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @Valid @RequestBody CreateCartDto request){
        cartService.createCart(customUserDetails.getUsername(), request);
        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{cartIdx}")
    public ResponseEntity<ResponseDto> updateCart(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @PathVariable int cartIdx,
            @Valid @RequestBody UpdateCartDto request) {
        cartService.updateCart(customUserDetails.getUsername(), cartIdx, request);
        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{cartIdx}")
    public ResponseEntity<ResponseDto> deleteCart(
            @AuthenticationPrincipal CustomUserDetailsDto customUserDetails,
            @PathVariable int cartIdx) {
        cartService.deleteCart(customUserDetails.getUsername(), cartIdx);
        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
