package com.accepted.givutake.funding.controller;

import com.accepted.givutake.funding.entity.CheerComments;
import com.accepted.givutake.funding.model.*;
import com.accepted.givutake.funding.service.CheerCommentService;
import com.accepted.givutake.funding.service.FundingReviewService;
import com.accepted.givutake.global.model.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/government-fundings/{fundingIdx}")
public class FundingController {

    private final CheerCommentService cheerCommentService;
    private final FundingReviewService fundingReviewService;

    // ========= 댓글 관련 ===========
    // fundingIdx에 해당하는 펀딩의 모든 댓글 조회
    @GetMapping("/comments")
    public ResponseEntity<ResponseDto> getCheerCommentListByFundingIdx(@PathVariable int fundingIdx) {

        List<CheerCommentViewDto> cheerCommentViewDtoList =
                cheerCommentService.getCheerCommentListByFundingIdx(fundingIdx)
                        .stream()
                        .map(CheerCommentViewDto::toDto)  // 각 CheerComments 객체를 CheerCommentViewDto로 변환
                        .collect(Collectors.toList());  // List로 변환

        ResponseDto responseDto = ResponseDto.builder()
                .data(cheerCommentViewDtoList)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // fundingIdx에 해당하는 펀딩의 commentIdx에 해당하는 댓글 조회
    @GetMapping("/comments/{commentIdx}")
    public ResponseEntity<ResponseDto> getCheerCommentByFundingIdxAndCommentIdx(@PathVariable int fundingIdx, @PathVariable int commentIdx) {
        CheerComments savedCheerComments = cheerCommentService.getCheerCommentByFundingIdxAndCommentIdx(fundingIdx, commentIdx);
        CheerCommentViewDto cheerCommentViewDto = CheerCommentViewDto.toDto(savedCheerComments);

        ResponseDto responseDto = ResponseDto.builder()
                .data(cheerCommentViewDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // jwt 토큰으로 댓글 작성
    @PostMapping("/comments")
    public ResponseEntity<ResponseDto> addCheerCommentByJwt(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int fundingIdx, @Valid @RequestBody CheerCommentAddDto cheerCommentAddDto) {
        String email = userDetails.getUsername();

        CheerComments savedCheerComments = cheerCommentService.addCheerCommentByEmail(email, fundingIdx, cheerCommentAddDto);
        CheerCommentViewDto cheerCommentViewDto = CheerCommentViewDto.toDto(savedCheerComments);

        ResponseDto responseDto = ResponseDto.builder()
                .data(cheerCommentViewDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // jwt 토큰으로 댓글 삭제
    @DeleteMapping("/comments/{commentIdx}")
    public ResponseEntity<ResponseDto> deleteCheerCommentByJwt(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int commentIdx, @PathVariable int fundingIdx) {
        String email = userDetails.getUsername();

        CheerComments deletedCheerComments = cheerCommentService.deleteCheerCommentByEmail(email, fundingIdx, commentIdx);
        CheerCommentViewDto cheerCommentViewDto = CheerCommentViewDto.toDto(deletedCheerComments);

        ResponseDto responseDto = ResponseDto.builder()
                .data(cheerCommentViewDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // ========= 후기 관련 ===========
    // 특정 펀딩의 펀딩 후기 조회
    @GetMapping("/review")
    public ResponseEntity<ResponseDto> getFundingReviewListByJwt(@PathVariable int fundingIdx) {
        FundingReviewViewDto savedFundingReviewDto = fundingReviewService.getFundingReviewListByFundingIdx(fundingIdx);

        ResponseDto responseDto = ResponseDto.builder()
                .data(savedFundingReviewDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // jwt 토큰으로 펀딩 후기 추가
    @PostMapping("/review")
    public ResponseEntity<ResponseDto> addFundingReviewByJwt(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int fundingIdx, @Valid @RequestBody FundingReviewAddDto fundingReviewAddDto) {
        String email = userDetails.getUsername();

        FundingReviewViewDto savedFundingReviewDto = fundingReviewService.addFundingReviewByEmail(email, fundingIdx, fundingReviewAddDto);

        ResponseDto responseDto = ResponseDto.builder()
                .data(savedFundingReviewDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // jwt 토큰으로 펀딩 후기 수정
    @PatchMapping("/review")
    public ResponseEntity<ResponseDto> modifyFundingReviewByJwt(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int fundingIdx, @Valid @RequestBody FundingReviewUpdateDto fundingReviewUpdateDto) {
        String email = userDetails.getUsername();

        FundingReviewViewDto modifiedFundingReviewDetailViewDto = fundingReviewService.modifyFundingReviewByEmail(email, fundingIdx, fundingReviewUpdateDto);

        ResponseDto responseDto = ResponseDto.builder()
                .data(modifiedFundingReviewDetailViewDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
