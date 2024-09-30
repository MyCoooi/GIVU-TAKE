package com.accepted.givutake.user.client.service;

import com.accepted.givutake.funding.service.FundingParticipantService;
import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.payment.service.OrderService;
import com.accepted.givutake.pdf.DonationParticipantsDto;
import com.accepted.givutake.pdf.DonationReceiptFormDto;
import com.accepted.givutake.pdf.PdfService;
import com.accepted.givutake.region.service.RegionService;
import com.accepted.givutake.user.client.entity.Addresses;
import com.accepted.givutake.user.client.model.AddressAddDto;
import com.accepted.givutake.user.client.model.AddressDetailViewDto;
import com.accepted.givutake.user.common.entity.Users;
import com.accepted.givutake.user.common.model.UserDto;
import com.accepted.givutake.user.common.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

    private final AddressService addressService;
    private final UserService userService;
    private final RegionService regionService;
    private final OrderService orderService;
    private final PdfService pdfService;
    private final FundingParticipantService fundingParticipantService;

    // 아이디가 email인 사용자의 모든 주소 조회
    public List<Addresses> getAddressesByEmail(String email) {
        // 1. email로 유저 조회
        UserDto savedUserDto = userService.getUserByEmail(email);
        Users savedUsers = savedUserDto.toEntity();

        // 2. 아이디가 email인 회원의 모든 주소록 가져오기(삭제 처리된 주소록 제외)
        return addressService.getAddressesByUsers(savedUsers);
    }

    // 아이디가 email인 사용자의 특정 주소 상세 조회
    public AddressDetailViewDto getAddressDetailByEmail(String email, int addressIdx) {
        // 1. email로 부터 userIdx값 가져오기
        UserDto savedUserDto = userService.getUserByEmail(email);
        int userIdx = savedUserDto.getUserIdx();

        // 2. addressIdx에 해당하는 주소 가져오기
        Addresses savedAddresses = addressService.getAddressByAddressIdx(addressIdx);

        // 3. userIdx값이 일치하지 않는 경우 조회 불가
        if (userIdx != savedAddresses.getUsers().getUserIdx()) {
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }

        return AddressDetailViewDto.toDto(savedAddresses);
    }

    // 아이디가 email인 사용자의 주소 추가
    public Addresses addAddressByEmail(String email, AddressAddDto addressAddDto) {
        // 1. email로 부터 userIdx값 가져오기
        UserDto savedUserDto = userService.getUserByEmail(email);
        Users savedUsers = savedUserDto.toEntity();

        // 2. 지역 코드 넣기
        String sido = addressAddDto.getSido();
        String sigungu = addressAddDto.getSigungu();
        int regionIdx = regionService.getRegionIdxBySidoAndSigungu(sido, sigungu);

        // 3. 대표 주소로 설정한다면, 이전의 대표 주소는 false 처리
        if (addressAddDto.getIsRepresentative()) {
            addressService.updateRepresentativeAddressFalse(savedUsers);
        }

        // 4. DB에 주소 추가
        Addresses addresses = addressAddDto.toEntity(savedUsers, regionIdx);
        return addressService.saveAddresses(addresses);
    }

    // 아이디가 email인 사용자의 주소 수정
    public AddressDetailViewDto modifyAddressByEmail(String email, int addressIdx, AddressAddDto addressAddDto) {
        // 1. email로 부터 userIdx값 가져오기
        UserDto savedUserDto = userService.getUserByEmail(email);
        int userIdx = savedUserDto.getUserIdx();

        // 2. DB에서 주소 조회
        Addresses savedAddresses = addressService.getAddressByAddressIdx(addressIdx);

        // 3. userIdx값이 일치하지 않는 경우 수정 불가
        if (userIdx != savedAddresses.getUsers().getUserIdx()) {
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }

        // 4. 지역 코드 넣기
        String sido = addressAddDto.getSido();
        String sigungu = addressAddDto.getSigungu();
        int regionIdx = regionService.getRegionIdxBySidoAndSigungu(sido, sigungu);

        // 5. 대표 주소로 설정한다면, 이전의 대표 주소는 false 처리
        if (addressAddDto.getIsRepresentative()) {
            addressService.updateRepresentativeAddressFalse(savedAddresses.getUsers());
        }

        // 5. 수정
        savedAddresses.setRegionIdx(regionIdx);
        savedAddresses.setAddressName(addressAddDto.getAddressName());
        savedAddresses.setZoneCode(addressAddDto.getZoneCode());
        savedAddresses.setJibunAddress(addressAddDto.getJibunAddress());
        savedAddresses.setDetailAddress(addressAddDto.getDetailAddress());
        savedAddresses.setBuildingName(addressAddDto.getBuildingName());
        savedAddresses.setApartment(addressAddDto.getIsApartment());
        savedAddresses.setBname(addressAddDto.getBname());
        savedAddresses.setBname1(addressAddDto.getBname1());
        savedAddresses.setRepresentative(addressAddDto.getIsRepresentative());
//        savedAddresses.setLatitude(addressUpdateDto.getLatitude());
//        savedAddresses.setLongitude(addressUpdateDto.getLongitude());

        // 6. DB에 저장
        return AddressDetailViewDto.toDto(addressService.saveAddresses(savedAddresses));
    }

    // 아이디가 email인 사용자의 특정 주소 삭제
    public Addresses deleteAddressByEmail(String email, int addressIdx) {
        // 1. 유저 조회
        UserDto savedUserDto = userService.getUserByEmail(email);
        Users savedUsers = savedUserDto.toEntity();

        // 2. 해당 회원의 주소가 총 1개라면 삭제 불가
        long cnt = addressService.countByUsers(savedUsers);
        if (cnt <= 1) {
            throw new ApiException(ExceptionEnum.NOT_ALLOWED_LAST_ADDRESS_DELETION_EXCEPTION);
        }

        // 2. addressIdx에 해당하는 주소 가져오기
        Addresses savedAddresses = addressService.getAddressByAddressIdx(addressIdx);

        // 3. userIdx값이 일치하지 않는 경우 삭제 불가
        if (savedUsers.getUserIdx() != savedAddresses.getUsers().getUserIdx()) {
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }

        // 4. 삭제
        return addressService.deleteAddressByAddressIdx(savedAddresses);
    }

    public void sendEmailDonationReceipt(String email) {
        // 1. DB에서 유저 조회
        UserDto savedUserDto = userService.getUserByEmail(email);
        Users savedUsers = savedUserDto.toEntity();

        // 2. 사용자의 펀딩 내역 가져오기(현재 연도 기록만)
        int nowYear = LocalDate.now().getYear();
        LocalDate startDate = LocalDate.of(nowYear, 1, 1);
        LocalDate endDate = LocalDate.of(nowYear, 12, 31);
        List<DonationParticipantsDto> fundingDonationParticipantsDtoList = fundingParticipantService.getFundingParticipantsListByEmail(email, startDate, endDate)
                .stream()
                .map(participant -> DonationParticipantsDto.fundingPariticipantsToDto(participant, ""))
                .collect(Collectors.toList());

        // 3. 답례품 구매 내역 가져오기(현재 연도 기록만)
        List<DonationParticipantsDto> orderDonationParticipantsDtoList = orderService.getOrdersCreatedDateBetweenByEmail(email, startDate, endDate)
                .stream()
                .map(orders -> DonationParticipantsDto.ordersToDto(orders, ""))
                .collect(Collectors.toList());

        // 4. 두 리스트 합치기
        List<DonationParticipantsDto> combinedList = new ArrayList<>(fundingDonationParticipantsDtoList);
        combinedList.addAll(orderDonationParticipantsDtoList);
        
        // 5. 최신 순으로 정렬
        Collections.sort(combinedList);

        // 6. 대표 주소 가져오기
        Addresses savedAddresses = addressService.getRepresentativeAddressesByUsers(savedUsers);

        // 7. PDF 파일로 생성
        DonationReceiptFormDto donationReceiptFormDto = DonationReceiptFormDto.builder()
                .userName(savedUserDto.getName())
                .userAddress(savedAddresses.getRoadAddress() + savedAddresses.getDetailAddress())
                .userPhone(savedUserDto.getMobilePhone())
                .donationParticipantsDtoList(combinedList)
                .build();

        pdfService.donationReceiptGenerate(donationReceiptFormDto);
    }

    // 나의 기부금 총액 조회
    public long calculateTotalFundingFeeByEmail(String email) {
        // 1. 사용자가 참여한 모든 펀딩의 기부금 조회
        int fundingPrice = fundingParticipantService.calculateTotalFundingFeeByEmail(email);

        // 2. 사용자의 총 답례품 금액 조회
        int giftPrice = orderService.calculateTotalOrderPriceByEmail(email);

        return (long) fundingPrice + (long) giftPrice;
    }
}
