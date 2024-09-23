package com.accepted.givutake.funding.service;

import com.accepted.givutake.funding.entity.FundingReviews;
import com.accepted.givutake.funding.entity.Fundings;
import com.accepted.givutake.funding.model.FundingReviewAddDto;
import com.accepted.givutake.funding.model.FundingReviewViewDto;
import com.accepted.givutake.funding.model.FundingReviewUpdateDto;
import com.accepted.givutake.funding.repository.FundingReviewsRepository;
import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.user.common.model.UserDto;
import com.accepted.givutake.user.common.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FundingReviewService {

    private final FundingReviewsRepository fundingReviewsRepository;
    private final UserService userService;
    private final FundingService fundingService;

    // fundingIdx에 해당하는 펀딩의 모든 펀딩 후기 조회
    public FundingReviewViewDto getFundingReviewListByFundingIdx(int fundingIdx) {
        Fundings fundings = fundingService.getFundingByFundingIdx(fundingIdx);

        FundingReviews fundingReviews = fundings.getFundingReviews();
        if (fundingReviews == null) {
            throw new ApiException(ExceptionEnum.NOT_FOUND_FUNDING_REVIEW_EXCEPTION);
        }

        return FundingReviewViewDto.toDto(fundingReviews);
    }

    // fundingReviewIdx로 펀딩 후기 조회
    public FundingReviews getFundingReviewsByFundingReviewIdx(int fundingReviewIdx) {
        Optional<FundingReviews> optionalFundingReviews = fundingReviewsRepository.findByReviewIdx(fundingReviewIdx);

        if (optionalFundingReviews.isPresent()) {
            return optionalFundingReviews.get();
        }
        else {
            throw new ApiException(ExceptionEnum.NOT_FOUND_FUNDING_REVIEW_EXCEPTION);
        }
    }
    
    // jwt 토큰으로 펀딩 후기 추가
    public FundingReviewViewDto addFundingReviewByEmail(String email, FundingReviewAddDto fundingReviewAddDto) {
        // 1. email로 부터 userIdx값 가져오기
        UserDto savedUserDto = userService.getUserByEmail(email);
        int userIdx = savedUserDto.getUserIdx();

        // 2. fundingIdx에 해당하는 펀딩이 존재하는지 확인
        int fundingIdx = fundingReviewAddDto.getFundingIdx();
        Fundings fundings = fundingService.getFundingByFundingIdx(fundingIdx);

        // 3. 존재한다면, 펀딩 후기를 추가하는 수혜자의 userIdx가 펀딩을 작성한 수혜자의 userIdx와 같은지 확인
        if (userIdx != fundings.getCorporation().getUserIdx()) {
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }

        // 3. 동일인이라면, 해당 펀딩의 후기가 이미 작성되어 있는지를 확인(펀딩 1개당 후기는 1개만 작성 가능)
        if (fundings.getFundingReviews() != null) {
            throw new ApiException(ExceptionEnum.NOT_ALLOWED_FUNDING_REVIEW_INSERTION_EXCEPTION);
        }

        // 4. 해당 펀딩의 후기가 작성되어 있지 않다면 DB에 펀딩 후기 추가
        FundingReviews fundingReviews = fundingReviewAddDto.toEntity(fundings);
        return FundingReviewViewDto.toDto(fundingReviewsRepository.save(fundingReviews));
    }

    // jwt 토큰으로 펀딩 후기 수정
    public FundingReviewViewDto modifyFundingReviewByEmail(String email, FundingReviewUpdateDto fundingReviewUpdateDto) {
        // 1. email로 부터 userIdx값 가져오기
        UserDto savedUserDto = userService.getUserByEmail(email);
        int userIdx = savedUserDto.getUserIdx();

        // 2. DB에서 펀딩 후기 조회
        int fundingReviewIdx = fundingReviewUpdateDto.getReviewIdx();
        FundingReviews savedFundingReviews = this.getFundingReviewsByFundingReviewIdx(fundingReviewIdx);

        // 3. userIdx값이 일치하지 않는 경우 수정 불가
        if (userIdx != savedFundingReviews.getFundings().getCorporation().getUserIdx()) {
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }

        // 4. 수정
        savedFundingReviews.setReviewContent(fundingReviewUpdateDto.getReviewContent());

        // 6. DB에 저장
        return FundingReviewViewDto.toDto(fundingReviewsRepository.save(savedFundingReviews));
    }
}
