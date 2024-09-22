package com.accepted.givutake.user.client.controller;

import com.accepted.givutake.global.model.ResponseDto;
import com.accepted.givutake.user.client.entity.Addresses;
import com.accepted.givutake.user.client.model.AddressAddDto;
import com.accepted.givutake.user.client.model.AddressDetailViewDto;
import com.accepted.givutake.user.client.model.AddressUpdateDto;
import com.accepted.givutake.user.client.model.AddressViewDto;
import com.accepted.givutake.user.client.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/client")
public class ClientController {

    private final ClientService clientService;

    // jwt 토큰으로 모든 주소 조회
    @GetMapping("/addresses")
    public ResponseEntity<ResponseDto> getAddressesByJwt(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();

        List<AddressViewDto> savedAddressViewDtoList = clientService.getAddressesByEmail(email);

        ResponseDto responseDto = ResponseDto.builder()
                .data(savedAddressViewDtoList)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // jwt 토큰으로 특정 회원의 특정 주소 상세 조회
    @GetMapping("/addresses/{addressIdx}")
    public ResponseEntity<ResponseDto> getAddressDetailByJwt(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("addressIdx") int addressIdx) {
        String email = userDetails.getUsername();
        AddressDetailViewDto addressDetailViewDto = clientService.getAddressDetailByEmail(email, addressIdx);

        ResponseDto responseDto = ResponseDto.builder()
                .data(addressDetailViewDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // jwt 토큰으로 주소 추가
    @PostMapping("/addresses")
    public ResponseEntity<ResponseDto> addAddressByJwt(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody AddressAddDto addressAddDto) {
        String email = userDetails.getUsername();

        clientService.addAddressByEmail(email, addressAddDto);

        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // jwt 토큰으로 주소 수정
    @PatchMapping("/addresses")
    public ResponseEntity<ResponseDto> modifyAddressByJwt(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody AddressUpdateDto addressUpdateDto) {
        String email = userDetails.getUsername();

        AddressDetailViewDto addressDetailViewDto = clientService.modifyAddressByEmail(email, addressUpdateDto);

        ResponseDto responseDto = ResponseDto.builder()
                .data(addressDetailViewDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // jwt 토큰으로 특정 주소 삭제
    @DeleteMapping("/addresses/{addressIdx}")
    public ResponseEntity<ResponseDto> deleteAddressByJwt(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("addressIdx") int addressIdx) {
        String email = userDetails.getUsername();

        clientService.deleteAddressByEmail(email, addressIdx);

        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
