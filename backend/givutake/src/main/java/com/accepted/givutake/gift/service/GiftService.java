package com.accepted.givutake.gift.service;

import com.accepted.givutake.gift.entity.GiftReviews;
import com.accepted.givutake.gift.entity.Gifts;
import com.accepted.givutake.gift.model.*;
import com.accepted.givutake.gift.repository.GiftRepository;
import com.accepted.givutake.gift.repository.GiftReviewRepository;
import com.accepted.givutake.global.entity.Categories;
import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.global.repository.CategoryRepository;
import com.accepted.givutake.user.common.entity.Users;
import com.accepted.givutake.user.common.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GiftService {

    private final GiftRepository giftRepository;
    private final GiftReviewRepository giftReviewRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public void createGift(String email, CreateGiftDto request) {
        Categories category = categoryRepository.findById(request.getCartegoryIdx()).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_CATEGORY_EXCEPTION));
        Users corporation = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_USER_WITH_EMAIL_EXCEPTION));
        Gifts newGift = Gifts.builder()
                .giftName(request.getGiftName())
                .corporations(corporation)
                .category(category)
                .giftThumbnail(request.getGiftThumbnail())
                .giftContent(request.getGiftContent())
                .price(request.getPrice())
            .build();
        giftRepository.save(newGift);
    }

    public List<GiftDto> getGifts(Integer corporationIdx, String search, Integer categoryIdx ,int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));


        Specification<Gifts> spec = Specification.where(null); // 동적 쿼리 생성
        if(corporationIdx != null){
            Optional<Users> corporation = userRepository.findById(corporationIdx);
            if (corporation.isPresent()) { // 특정 사용자가 등록한 물품
                spec = spec.and((root, query, cb) -> cb.equal(root.get("corporations"), corporation.get()));
            }
        }
        if(categoryIdx != null) {
            Optional<Categories> category = categoryRepository.findById(categoryIdx);
            if (category.isPresent()) { // 카테고리별 분류
                spec = spec.and((root, query, cb) -> cb.equal(root.get("category"), category.get()));
            }
        }

        if (!search.isEmpty()) { // 검색어 필터링
            spec = spec.and((root, query, cb) -> cb.like(root.get("giftName"), "%" + search + "%"));
        }

        Page<Gifts> giftList = giftRepository.findAll(spec, pageable);

        return giftList.map(gift -> GiftDto.builder()
                .giftIdx(gift.getGiftIdx())
                .giftName(gift.getGiftName())
                .giftThumbnail(gift.getGiftThumbnail())
                .corporationIdx(gift.getCorporations().getUserIdx())
                .corporationName(gift.getCorporations().getName())
                .cartegoryIdx(gift.getCategory().getCategoryIdx())
                .categoryName(gift.getCategory().getCategoryName())
                .price(gift.getPrice())
                .build()
        ).toList();
    }

    public GiftDto getGift(int giftIdx) {
        Gifts gift = giftRepository.findById(giftIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_GIFT_EXCEPTION));
        return GiftDto.builder()
                .giftIdx(gift.getGiftIdx())
                .giftName(gift.getGiftName())
                .giftThumbnail(gift.getGiftThumbnail())
                .giftContent(gift.getGiftContent())
                .corporationIdx(gift.getCorporations().getUserIdx())
                .corporationName(gift.getCorporations().getName())
                .cartegoryIdx(gift.getCategory().getCategoryIdx())
                .categoryName(gift.getCategory().getCategoryName())
                .price(gift.getPrice())
                .createdDate(gift.getCreatedDate())
                .modifiedDate(gift.getModifiedDate())
                .build();
    }

    public void updateGift(String email, int giftIdx, UpdateGiftDto request) {
        Gifts gift = giftRepository.findById(giftIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_GIFT_EXCEPTION));
        if(!gift.getCorporations().getEmail().equals(email)){
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }
        gift.setGiftName(request.getGiftName());
        gift.setGiftThumbnail(request.getGiftThumbnail());
        gift.setGiftContent(request.getGiftContent());
        gift.setCategory(categoryRepository.findById(request.getCartegoryIdx()).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_CATEGORY_EXCEPTION)));
        gift.setPrice(request.getPrice());
        giftRepository.save(gift);
    }

    public void deleteGift(String email, int giftIdx) {
        Gifts gift = giftRepository.findById(giftIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_GIFT_EXCEPTION));
        if(!gift.getCorporations().getEmail().equals(email)){
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }
        giftRepository.delete(gift);
    }

    public void createGiftReview(String email, CreateGiftReviewDto request) {
        Gifts gift = giftRepository.findById(request.getGiftIdx()).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_GIFT_EXCEPTION));
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_USER_WITH_EMAIL_EXCEPTION));
        GiftReviews giftReviews = GiftReviews.builder()
                .reviewTitle(request.getReviewTitle())
                .reviewContent(request.getReviewContent())
                .gifts(gift)
                .users(user)
                .build();
        giftReviewRepository.save(giftReviews);
    }

    public List<GiftReviewDto> getGiftReviews(int giftIdx, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));

        Gifts gifts = giftRepository.findById(giftIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_GIFT_EXCEPTION));

        Page<GiftReviews> reviewList = giftReviewRepository.findByGifts(gifts, pageable);

        return reviewList.map(review -> GiftReviewDto.builder()
                .reviewIdx(review.getReviewIdx())
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .giftIdx(review.getGifts().getGiftIdx())
                .userIdx(review.getUsers().getUserIdx())
                .userName(review.getUsers().getName())
                .createdDate(review.getCreatedDate())
                .modifiedDate(review.getModifiedDate())
                .build()
        ).toList();
    }

    public List<GiftReviewDto> getUserReviews(String email, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));

        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_USER_WITH_EMAIL_EXCEPTION));

        Page<GiftReviews> reviewList = giftReviewRepository.findByUsers(user, pageable);

        return reviewList.map(review -> GiftReviewDto.builder()
                .reviewIdx(review.getReviewIdx())
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .giftIdx(review.getGifts().getGiftIdx())
                .userIdx(review.getUsers().getUserIdx())
                .userName(review.getUsers().getName())
                .createdDate(review.getCreatedDate())
                .modifiedDate(review.getModifiedDate())
                .build()
        ).toList();
    }

    public void updateGiftReviews(String email, int reviewIdx, UpdateGiftReviewDto request) {
        GiftReviews review = giftReviewRepository.findById(reviewIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_GIFT_REVIEW_EXCEPTION));
        if(!review.getUsers().getEmail().equals(email)){
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }
        review.setReviewTitle(request.getReviewTitle());
        review.setReviewContent(request.getReviewContent());
        giftReviewRepository.save(review);
    }

                                     public void deleteGiftReviews(String email, int reviewIdx) {
        GiftReviews review = giftReviewRepository.findById(reviewIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_GIFT_REVIEW_EXCEPTION));
        if(!review.getUsers().getEmail().equals(email)){
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }
        giftReviewRepository.delete(review);
    }

}
