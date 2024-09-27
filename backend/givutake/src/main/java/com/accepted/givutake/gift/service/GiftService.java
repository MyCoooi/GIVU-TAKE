package com.accepted.givutake.gift.service;

import com.accepted.givutake.gift.entity.GiftReviewLiked;
import com.accepted.givutake.gift.entity.GiftReviews;
import com.accepted.givutake.gift.entity.Gifts;
import com.accepted.givutake.gift.model.*;
import com.accepted.givutake.gift.repository.GiftRepository;
import com.accepted.givutake.gift.repository.GiftReviewLikedRepository;
import com.accepted.givutake.gift.repository.GiftReviewRepository;
import com.accepted.givutake.payment.entity.Orders;
import com.accepted.givutake.payment.repository.OrderRepository;
import com.accepted.givutake.global.entity.Categories;
import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.global.repository.CategoryRepository;
import com.accepted.givutake.payment.service.OrderService;
import com.accepted.givutake.user.common.entity.Users;
import com.accepted.givutake.user.common.repository.UsersRepository;
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
    private final GiftReviewLikedRepository giftReviewLikedRepository;
    private final CategoryRepository categoryRepository;
    private final UsersRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public Gifts createGift(String email, CreateGiftDto request) {
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
        return giftRepository.save(newGift);
    }

    public List<GiftDto> getGifts(Integer corporationIdx, String search, Integer categoryIdx ,int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));

        Specification<Gifts> spec = Specification.where((root, query, cb) -> cb.equal(root.get("isDelete"), false)); // 동적 쿼리 생성

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
                .corporationSido(gift.getCorporations().getRegion().getSido())
                .corporationSigungu(gift.getCorporations().getRegion().getSigungu())
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
                .corporationSido(gift.getCorporations().getRegion().getSido())
                .corporationSigungu(gift.getCorporations().getRegion().getSigungu())
                .cartegoryIdx(gift.getCategory().getCategoryIdx())
                .categoryName(gift.getCategory().getCategoryName())
                .price(gift.getPrice())
                .createdDate(gift.getCreatedDate())
                .modifiedDate(gift.getModifiedDate())
                .build();
    }

    public Gifts updateGift(String email, int giftIdx, UpdateGiftDto request) {
        Gifts gift = giftRepository.findById(giftIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_GIFT_EXCEPTION));
        if(!gift.getCorporations().getEmail().equals(email)){
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }
        gift.setGiftName(request.getGiftName());
        gift.setGiftThumbnail(request.getGiftThumbnail());
        gift.setGiftContent(request.getGiftContent());
        gift.setCategory(categoryRepository.findById(request.getCartegoryIdx()).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_CATEGORY_EXCEPTION)));
        gift.setPrice(request.getPrice());
        return giftRepository.save(gift);
    }

    public Gifts deleteGift(String authority, String email, int giftIdx) {
        Gifts gift = giftRepository.findById(giftIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_GIFT_EXCEPTION));
        if(!(gift.getCorporations().getEmail().equals(email)||authority.equals("ROLE_ADMIN"))){
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }
        gift.setDelete(true);
        return giftRepository.save(gift);
    }

    public boolean IsWriteGiftReview(String email, int orderIdx){
        Orders order = orderRepository.findById(orderIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ORDER_EXCEPTION));
        if(!order.getUsers().getEmail().equals(email)){
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }
        return giftReviewRepository.existsByOrdersAndIsDeleteFalse(order);
    }

    public void createGiftReview(String email, CreateGiftReviewDto request) {
        Gifts gift = giftRepository.findById(request.getGiftIdx()).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_GIFT_EXCEPTION));
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_USER_WITH_EMAIL_EXCEPTION));
        Orders order = orderRepository.findById(request.getOrderIdx()).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ORDER_EXCEPTION));

        if(IsWriteGiftReview(email, request.getOrderIdx())){
            throw new ApiException(ExceptionEnum.NOT_ALLOWED_GIFT_REVIEW_INSERTION_EXCEPTION);
        }

        GiftReviews giftReviews = GiftReviews.builder()
                .reviewContent(request.getReviewContent())
                .gifts(gift)
                .users(user)
                .orders(order)
                .build();
        giftReviewRepository.save(giftReviews);
    }

    public List<GiftReviewDto> getGiftReviews(int giftIdx, boolean isOrderLiked, int pageNo, int pageSize) {

        Pageable pageable = null;

        if(isOrderLiked){
            pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "likedCount"));
        }else {
            pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        }
        Gifts gifts = giftRepository.findById(giftIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_GIFT_EXCEPTION));

        Specification<GiftReviews> spec = (root, query, cb) -> {
            return cb.and(
                    cb.equal(root.get("gifts"), gifts),
                    cb.equal(root.get("isDelete"), false)
            );
        };

        Page<GiftReviews> reviewList = giftReviewRepository.findAll(spec, pageable);

        return reviewList.map(review -> GiftReviewDto.builder()
                .reviewIdx(review.getReviewIdx())
                .reviewContent(review.getReviewContent())
                .giftIdx(review.getGifts().getGiftIdx())
                .giftName(review.getGifts().getGiftName())
                .giftThumbnail(review.getGifts().getGiftThumbnail())
                .userIdx(review.getUsers().getUserIdx())
                .userName(review.getUsers().getName())
                .userProfileImage(review.getUsers().getProfileImageUrl())
                .orderIdx(review.getOrders().getOrderIdx())
                .likedCount(review.getLikedCount())
                .createdDate(review.getCreatedDate())
                .modifiedDate(review.getModifiedDate())
                .build()
        ).toList();
    }

    public List<GiftReviewDto> getUserReviews(String email, boolean isOrderLiked, int pageNo, int pageSize) {
        Pageable pageable = null;

        if(isOrderLiked){
            pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "likedCount"));
        }else {
            pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        }

        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_USER_WITH_EMAIL_EXCEPTION));

        Specification<GiftReviews> spec = (root, query, cb) -> {
            return cb.and(
                    cb.equal(root.get("users"), user),
                    cb.equal(root.get("isDelete"), false)
            );
        };

        Page<GiftReviews> reviewList = giftReviewRepository.findAll(spec, pageable);

        return reviewList.map(review -> GiftReviewDto.builder()
                .reviewIdx(review.getReviewIdx())
                .reviewContent(review.getReviewContent())
                .giftIdx(review.getGifts().getGiftIdx())
                .giftName(review.getGifts().getGiftName())
                .giftThumbnail(review.getGifts().getGiftThumbnail())
                .userIdx(review.getUsers().getUserIdx())
                .userName(review.getUsers().getName())
                .userProfileImage(review.getUsers().getProfileImageUrl())
                .orderIdx(review.getOrders().getOrderIdx())
                .likedCount(review.getLikedCount())
                .createdDate(review.getCreatedDate())
                .modifiedDate(review.getModifiedDate())
                .build()
        ).toList();
    }

    public GiftReviewDto getUserReview(String email, int reviewIdx){
        GiftReviews review = giftReviewRepository.findById(reviewIdx).orElseThrow(()->new ApiException(ExceptionEnum.NOT_FOUND_GIFT_REVIEW_EXCEPTION));
        if(!review.getUsers().getEmail().equals(email)){
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }
        if(review.isDelete()){
            throw new ApiException(ExceptionEnum.GIFT_REVIEW_ALREADY_DELETED_EXCEPTION);
        }
        return GiftReviewDto.builder()
                .reviewIdx(reviewIdx)
                .reviewContent(review.getReviewContent())
                .giftIdx(review.getGifts().getGiftIdx())
                .giftName(review.getGifts().getGiftName())
                .giftThumbnail(review.getGifts().getGiftThumbnail())
                .userIdx(review.getUsers().getUserIdx())
                .userName(review.getUsers().getName())
                .userProfileImage(review.getUsers().getProfileImageUrl())
                .orderIdx(review.getOrders().getOrderIdx())
                .createdDate(review.getCreatedDate())
                .modifiedDate(review.getModifiedDate())
                .build();
    }

    public void updateGiftReviews(String email, int reviewIdx, UpdateGiftReviewDto request) {
        GiftReviews review = giftReviewRepository.findById(reviewIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_GIFT_REVIEW_EXCEPTION));
        if(!review.getUsers().getEmail().equals(email)){
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }
        review.setReviewContent(request.getReviewContent());
        giftReviewRepository.save(review);
    }
    public void deleteGiftReviews(String authority, String email, int reviewIdx) {
        GiftReviews review = giftReviewRepository.findById(reviewIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_GIFT_REVIEW_EXCEPTION));
        if(!(review.getUsers().getEmail().equals(email)||authority.equals("ROLE_ADMIN"))){
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }
        review.setDelete(true);
        giftReviewRepository.save(review);
    }

    public boolean isLiked(String email, int reviewIdx) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_USER_WITH_EMAIL_EXCEPTION));
        return giftReviewLikedRepository.existsByUserAndGiftReviews_ReviewIdx(user, reviewIdx);
    }


    public void createLiked(String email, int reviewIdx) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_USER_WITH_EMAIL_EXCEPTION));
        GiftReviews review = giftReviewRepository.findById(reviewIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_GIFT_REVIEW_EXCEPTION));

        if(review.isDelete()){
            throw new ApiException(ExceptionEnum.NOT_ALLOWED_OPERATION_ON_DELETED_REVIEW_EXCEPTION);
        }

        if(isLiked(email,reviewIdx)){
            throw new ApiException(ExceptionEnum.NOT_ALLOWED_LIKED_INSERTION_EXCEPTION);
        }
        GiftReviewLiked newLiked = GiftReviewLiked.builder()
                .user(user)
                .giftReviews(review)
                .build();
        giftReviewLikedRepository.save(newLiked);
        review.setLikedCount(review.getLikedCount()+1);
        giftReviewRepository.save(review);
    }

    public void deleteLiked(String email, int reviewIdx) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_USER_WITH_EMAIL_EXCEPTION));
        GiftReviews review = giftReviewRepository.findById(reviewIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_GIFT_REVIEW_EXCEPTION));

        if(review.isDelete()){
            throw new ApiException(ExceptionEnum.NOT_ALLOWED_OPERATION_ON_DELETED_REVIEW_EXCEPTION);
        }

        if(!isLiked(email,reviewIdx)){
            throw new ApiException(ExceptionEnum.NOT_ALLOWED_LIKED_DELETION_EXCEPTION);
        }
        GiftReviewLiked liked = giftReviewLikedRepository.findByUserAndGiftReviews(user, review)
                .orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_GIFT_REVIEW_LIKED_EXCEPTION));
        giftReviewLikedRepository.delete(liked);
        review.setLikedCount(review.getLikedCount()-1);
        giftReviewRepository.save(review);
    }
}
