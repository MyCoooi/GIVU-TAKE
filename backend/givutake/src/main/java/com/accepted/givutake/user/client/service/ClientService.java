package com.accepted.givutake.user.client.service;

import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.global.service.RegionService;
import com.accepted.givutake.user.client.entity.Addresses;
import com.accepted.givutake.user.client.model.AddressAddDto;
import com.accepted.givutake.user.client.model.AddressDetailViewDto;
import com.accepted.givutake.user.client.model.AddressViewDto;
import com.accepted.givutake.user.common.model.UserDto;
import com.accepted.givutake.user.common.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

    private final AddressService addressService;
    private final UserService userService;
    private final RegionService regionService;

    // 아이디가 email인 사용자의 모든 주소 조회
    public List<Addresses> getAddressesByEmail(String email) {
        // 1. email로 부터 userIdx값 가져오기
        UserDto savedUserDto = userService.getUserByEmail(email);
        int userIdx = savedUserDto.getUserIdx();

        // 2. 아이디가 email인 회원의 모든 주소록 가져오기(삭제 처리된 주소록 제외)
        return addressService.getAddressesByUserIdx(userIdx);
    }

    // 아이디가 email인 사용자의 특정 주소 상세 조회
    public AddressDetailViewDto getAddressDetailByEmail(String email, int addressIdx) {
        // 1. email로 부터 userIdx값 가져오기
        UserDto savedUserDto = userService.getUserByEmail(email);
        int userIdx = savedUserDto.getUserIdx();

        // 2. addressIdx에 해당하는 주소 가져오기
        Addresses savedAddresses = addressService.getAddressByAddressIdx(addressIdx);

        // 3. userIdx값이 일치하지 않는 경우 조회 불가
        if (userIdx != savedAddresses.getUserIdx()) {
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }

        return AddressDetailViewDto.toDto(savedAddresses);
    }

    // 아이디가 email인 사용자의 주소 추가
    public Addresses addAddressByEmail(String email, AddressAddDto addressAddDto) {
        // 1. email로 부터 userIdx값 가져오기
        UserDto savedUserDto = userService.getUserByEmail(email);
        int userIdx = savedUserDto.getUserIdx();

        // 2. 지역 코드 넣기
        String sido = addressAddDto.getSido();
        String sigungu = addressAddDto.getSigungu();
        int regionIdx = regionService.getRegionIdxBySidoAndSigungu(sido, sigungu);

        // 3. DB에 주소 추가
        Addresses addresses = addressAddDto.toEntity(userIdx, regionIdx);
        return addressService.saveAddress(addresses);
    }

    // 아이디가 email인 사용자의 주소 수정
    public AddressDetailViewDto modifyAddressByEmail(String email, int addressIdx, AddressAddDto addressAddDto) {
        // 1. email로 부터 userIdx값 가져오기
        UserDto savedUserDto = userService.getUserByEmail(email);
        int userIdx = savedUserDto.getUserIdx();

        // 2. DB에서 주소 조회
        Addresses savedAddresses = addressService.getAddressByAddressIdx(addressIdx);

        // 3. userIdx값이 일치하지 않는 경우 수정 불가
        if (userIdx != savedAddresses.getUserIdx()) {
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }

        // 4. 지역 코드 넣기
        String sido = addressAddDto.getSido();
        String sigungu = addressAddDto.getSigungu();
        int regionIdx = regionService.getRegionIdxBySidoAndSigungu(sido, sigungu);

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
        return AddressDetailViewDto.toDto(addressService.saveAddress(savedAddresses));
    }

    // 아이디가 email인 사용자의 특정 주소 삭제
    public Addresses deleteAddressByEmail(String email, int addressIdx) {
        // 1. email로 부터 userIdx값 가져오기
        UserDto savedUserDto = userService.getUserByEmail(email);
        int userIdx = savedUserDto.getUserIdx();

        // 2. 해당 회원의 주소가 총 1개라면 삭제 불가
        long cnt = addressService.countByUserIdx(userIdx);
        if (cnt <= 1) {
            throw new ApiException(ExceptionEnum.NOT_ALLOWED_LAST_ADDRESS_DELETION_EXCEPTION);
        }

        // 2. addressIdx에 해당하는 주소 가져오기
        Addresses savedAddresses = addressService.getAddressByAddressIdx(addressIdx);

        // 3. userIdx값이 일치하지 않는 경우 삭제 불가
        if (userIdx != savedAddresses.getUserIdx()) {
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }

        // 4. 삭제
        return addressService.deleteAddressByAddressIdx(savedAddresses);
    }
}
