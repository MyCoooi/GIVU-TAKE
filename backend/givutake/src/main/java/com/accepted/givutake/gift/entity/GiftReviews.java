package com.accepted.givutake.gift.entity;

import com.accepted.givutake.global.entity.BaseTimeEntity;
import com.accepted.givutake.user.common.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftReviews extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 후기 ID
    @Column(name = "review_idx")
    private int reviewIdx;

    @Column(name = "review_title", nullable = false, length = 50) // 후기 제목
    private String reviewTitle;

    @Column(name = "review_content", nullable = false, length = 6000) // 후기 내용
    private String reviewContent;

    @ManyToOne(targetEntity = Gifts.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "gift_idx", referencedColumnName = "gift_idx") // 답례품 ID 외래키 설정
    private Gifts gifts;

    @ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_idx", referencedColumnName = "user_idx")// 회원 ID 외래키 설정
    private Users users;
}
