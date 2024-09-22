package com.accepted.givutake.user.client.service;

import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.user.client.entity.Addresses;
import com.accepted.givutake.user.client.model.AddressViewDto;
import com.accepted.givutake.user.client.repository.AddressRepository;
import com.accepted.givutake.user.common.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;
    private final UsersRepository usersRepository;

    // DB에 주소 정보 저장
    public Addresses saveAddress(Addresses addresses) {
        // 대표 주소일 때, 기존의 대표 주소가 있으면 비활성화
        if (addresses.isRepresentative()) {
            // JPA의 영속성 문제로 이미 addresses가 db에서 대표주소로 설정되어 있다면, isRepresentative값이 true로 변경되지 않는 문제가 있어 아래 과정 추가
            if (!addressRepository.findIsRepresentativeByAddressIdx(addresses.getAddressIdx())) {
                addressRepository.updateRepresentativeStatusFalse(addresses.getUserIdx());
            }
        }

        return addressRepository.save(addresses);
    }

    // userIdx에 해당하는 유저의 모든 주소록 조회
    // (삭제 처리된 주소 제외하며, isRepresentative가 true인 정보 먼저 조회)
    public List<AddressViewDto> getAddressesByUserIdx(int userIdx) {
        List<Addresses> savedAddressesList =  addressRepository.findByUserIdxAndIsDeletedFalseOrderByIsRepresentativeDesc(userIdx);

        // AddressViewDto로 변환
        List<AddressViewDto> addressViewDtoList = savedAddressesList.stream()
                .map(AddressViewDto::toDto)
                .collect(Collectors.toList());

        return addressViewDtoList;
    }

    // addressIdx에 해당하는 주소 조회(삭제된 주소는 조회 불가)
    public Addresses getAddressByAddressIdx(int addressIdx) {
        Optional<Addresses> optionalExistingAddresses = addressRepository.findByAddressIdx(addressIdx);

        // DB에 존재하지 않을 경우
        if (!optionalExistingAddresses.isPresent()) {
            throw new ApiException(ExceptionEnum.NOT_FOUND_ADDRESSES_EXCEPTION);
        }

        // 삭제된 주소일 경우 조회 불가
        Addresses savedAddresses = optionalExistingAddresses.get();
        if (savedAddresses.isDeleted()) {
            throw new ApiException(ExceptionEnum.ADDRESSES_ALREADY_DELETED_EXCEPTION);
        }

        return savedAddresses;
    }

    // addressIdx에 해당하는 주소 삭제
    public void deleteAddressByAddressIdx(Addresses addresses) {
        addresses.setDeleted(true);
        addressRepository.save(addresses);
    }

    // userIdx에 해당하는 사용자의 총 주소 개수 조회
    public long countByUserIdx(int userIdx) {
        return addressRepository.countByUserIdx(userIdx);
    }
}
