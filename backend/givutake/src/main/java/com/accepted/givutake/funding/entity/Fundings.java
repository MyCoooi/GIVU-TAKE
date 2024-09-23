package com.accepted.givutake.funding.entity;

import com.accepted.givutake.global.entity.BaseTimeEntity;
import com.accepted.givutake.global.entity.Categories;
import com.accepted.givutake.user.common.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

@Table(name="Fundings")
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fundings extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funding_idx", nullable = false)
    private int fundingIdx;

    @Column(name = "funding_title", length = 30, nullable = false)
    private String fundingTitle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "corporation_idx", nullable = false)
    private Users corporation;

    @OneToOne(mappedBy = "fundings", orphanRemoval = true, fetch = FetchType.EAGER)
    private FundingReviews fundingReviews;

    @Builder.Default
    @OneToMany(mappedBy = "fundings", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CheerComments> cheerCommentsList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "fundings", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FundingParticipants> fundingParticipantsList = new ArrayList<>();

    @Column(name = "goal_money", nullable = false)
    private int goalMoney;

    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    @Column(name = "funding_thumbnail", nullable = true, length = 2048)
    private String fundingThumbnail;

    @Column(name = "funding_type", nullable = false, length = 1)
    private char fundingType;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;
}
