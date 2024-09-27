package com.accepted.givutake.user.client.controller;

import com.accepted.givutake.funding.model.FundingParticipantViewDto;
import com.accepted.givutake.funding.service.FundingParticipantService;
import com.accepted.givutake.global.model.ResponseDto;
import com.accepted.givutake.user.client.entity.Addresses;
import com.accepted.givutake.user.client.model.AddressAddDto;
import com.accepted.givutake.user.client.model.AddressDetailViewDto;
import com.accepted.givutake.user.client.model.AddressViewDto;
import com.accepted.givutake.user.client.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/client")
public class ClientController {

    private final ClientService clientService;
    private final FundingParticipantService fundingParticipantService;

    // jwt 토큰으로 모든 주소 조회
    @GetMapping("/addresses")
    public ResponseEntity<ResponseDto> getAddressesByJwt(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();

        // AddressViewDto로 변환
        List<AddressViewDto> addressViewDtoList = clientService.getAddressesByEmail(email).stream()
                .map(AddressViewDto::toDto)
                .collect(Collectors.toList());

        ResponseDto responseDto = ResponseDto.builder()
                .data(addressViewDtoList)
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

        Addresses savedAddresses = clientService.addAddressByEmail(email, addressAddDto);
        AddressDetailViewDto addressDetailViewDto = AddressDetailViewDto.toDto(savedAddresses);

        ResponseDto responseDto = ResponseDto.builder()
                .data(addressDetailViewDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // jwt 토큰으로 주소 수정
    @PatchMapping("/addresses/{addressIdx}")
    public ResponseEntity<ResponseDto> modifyAddressByJwt(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int addressIdx, @Valid @RequestBody AddressAddDto addressAddDto) {
        String email = userDetails.getUsername();

        AddressDetailViewDto addressDetailViewDto = clientService.modifyAddressByEmail(email, addressIdx, addressAddDto);

        ResponseDto responseDto = ResponseDto.builder()
                .data(addressDetailViewDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // jwt 토큰으로 특정 주소 삭제
    @DeleteMapping("/addresses/{addressIdx}")
    public ResponseEntity<ResponseDto> deleteAddressByJwt(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("addressIdx") int addressIdx) {
        String email = userDetails.getUsername();

        Addresses deletedAddresses = clientService.deleteAddressByEmail(email, addressIdx);
        AddressDetailViewDto addressDetailViewDto = AddressDetailViewDto.toDto(deletedAddresses);

        ResponseDto responseDto = ResponseDto.builder()
                .data(addressDetailViewDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //  ===== 펀딩 내역 관련 =====
    // 일정 기간 동안의 자신의 펀딩 내역 조회
    @GetMapping("/funding-participants")
    public ResponseEntity<ResponseDto> getFundingParticipantsListByJwt(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate) {
        String email = userDetails.getUsername();

        List<FundingParticipantViewDto> fundingParticipantViewDtoList =
                fundingParticipantService.getFundingParticipantsListByEmail(email, startDate, endDate)
                        .stream()
                        .map(FundingParticipantViewDto::toDto)
                        .collect(Collectors.toList());

        ResponseDto responseDto = ResponseDto.builder()
                .data(fundingParticipantViewDtoList)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 자신이 참여한 펀딩 수 조회
    @GetMapping("/funding-participants/count")
    public ResponseEntity<ResponseDto> getFundingParticipantsCountByJwt(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();

        long count = fundingParticipantService.getCountByEmail(email);

        Map<String, Long> map = new HashMap<>();
        map.put("count",count);

        ResponseDto responseDto = ResponseDto.builder()
                .data(map)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // === 기부금 영수증 관련 =====
    // 이메일로 기부금 영수증 보내기
    @GetMapping("/donation-receipt")
    public ResponseEntity<ResponseDto> sendEmailDonationReceipt(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        
        clientService.sendEmailDonationReceipt(email);

        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
