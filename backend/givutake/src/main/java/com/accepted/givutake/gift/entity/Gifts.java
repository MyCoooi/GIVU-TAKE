package com.accepted.givutake.gift.entity;

import com.accepted.givutake.global.entity.BaseTimeEntity;
import com.accepted.givutake.global.entity.Categories;
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
@Table(name = "gifts")
public class Gifts extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 답례품 ID
    @Column(name = "gift_idx")
    private int giftIdx;

    @Column(name = "gift_name", nullable = false, length = 30) // 답례품명
    private String giftName;

    @ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "corporation_idx", referencedColumnName = "user_idx")// 지자체 ID 외래키 설정
    private Users corporations;

    @ManyToOne(targetEntity = Categories.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_idx", referencedColumnName = "category_idx") // 카테고리 ID 외래키 설정
    private Categories category;

    @Builder.Default
    @Column(name = "gift_thumbnail", length = 2048) // 답례품 썸네일
    private String giftThumbnail = ""; // 기본 이미지 설정(해야할 일)

    @Column(name = "gift_content", nullable = false, length = 6000) // 답례품 설명
    private String giftContent;

    @Column(name = "price", nullable = false)
    private int price;
}
