package com.accepted.givutake.funding.service;

import com.accepted.givutake.funding.entity.CheerComments;
import com.accepted.givutake.funding.entity.Fundings;
import com.accepted.givutake.funding.model.CheerCommentAddDto;
import com.accepted.givutake.funding.model.CheerCommentViewDto;
import com.accepted.givutake.funding.repository.CheerCommentsRepository;
import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.user.common.model.UserDto;
import com.accepted.givutake.user.common.service.UserService;
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
public class CheerCommentService {

    private final UserService userService;
    private final FundingService fundingService;
    private final CheerCommentsRepository cheerCommentsRepository;

    // fundingIdx에 해당하는 펀딩의 모든 댓글 조회(삭제된 댓글은 조회x)
    public List<CheerComments> getCheerCommentListByFundingIdx(int fundingIdx) {
        // 1. fundingIdx에 해당하는 펀딩의 삭제 여부 검사
        Fundings savedFundings = fundingService.getFundingByFundingIdx(fundingIdx);

        // 2. 해당 펀딩의 삭제되지 않은 모든 댓글 조회
        return cheerCommentsRepository.findByFundingsAndIsDeletedFalseOrderByCommentIdxDesc(savedFundings);
    }

    // fundingIdx에 해당하는 펀딩의 commentIdx에 해당하는 댓글 상세 조회
    public CheerComments getCheerCommentByFundingIdxAndCommentIdx(int fundingIdx, int commentIdx) {
        log.info("fundingIdx {}, commentIdx {}", fundingIdx, commentIdx);
        // 1. 펀딩 정보 조회
        Fundings savedFundings = fundingService.getFundingByFundingIdx(fundingIdx);
        
        // 2. 댓글 정보 조회
        Optional<CheerComments> optionalCheerComments = cheerCommentsRepository.findByCommentIdxAndFundings_FundingIdx(commentIdx, fundingIdx);

        if (optionalCheerComments.isPresent()) {
            CheerComments cheerComments = optionalCheerComments.get();
            
            // 조회하려는 펀딩과 댓글이 작성된 펀딩이 일치하는지 확인
            if (savedFundings.getFundingIdx() != fundingIdx) {
                throw new SecurityException();
            }

            // 이미 삭제된 댓글이라면 조회 불가
            if (cheerComments.isDeleted()) {
                throw new ApiException(ExceptionEnum.CHEER_COMMENT_ALREADY_DELETED_EXCEPTION);
            }

            return cheerComments;
        }

        throw new ApiException(ExceptionEnum.NOT_FOUND_CHEER_COMMENT_EXCEPTION);
    }

    // 댓글 작성
    public CheerComments addCheerCommentByEmail(String email, int fundingIdx, CheerCommentAddDto cheerCommentAddDto) {
        // 1. 이메일에 해당하는 유저 조회
        UserDto savedUserDto = userService.getUserByEmail(email);

        // 2. fundingIdx에 해당하는 펀딩 조회
        Fundings fundings = fundingService.getFundingByFundingIdx(fundingIdx);

        // 3. DB에 저장
        CheerComments savedCheerComments = cheerCommentsRepository.save(cheerCommentAddDto.toEntity(fundings, savedUserDto.toEntity()));

        return savedCheerComments;
    }

    // 댓글 삭제
    public CheerComments deleteCheerCommentByEmail(String email, int fundingIdx, int commentIdx) {
        // 1. 이메일에 해당하는 유저 조회
        UserDto savedUserDto = userService.getUserByEmail(email);

        // 2. commentIdx에 해당하는 댓글 조회
        CheerComments savedCheerComments = this.getCheerCommentByFundingIdxAndCommentIdx(fundingIdx, commentIdx);

        // 3. 삭제하려는 펀딩의 댓글이 맞는지 확인
        if (savedCheerComments.getFundings().getFundingIdx() != fundingIdx) {
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }

        // 4. 댓글을 작성한 사람과 삭제하려는 사람이 일치하는지 확인
        if (savedUserDto.getUserIdx() != savedCheerComments.getUsers().getUserIdx()) {
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }

        // 5. 삭제
        int cnt = cheerCommentsRepository.updateIsDeletedTrueByCommentIdx(commentIdx);

        if (cnt == 0) {
            throw new ApiException(ExceptionEnum.INTERNAL_SERVER_ERROR);
        }

        return savedCheerComments;
    }


}
