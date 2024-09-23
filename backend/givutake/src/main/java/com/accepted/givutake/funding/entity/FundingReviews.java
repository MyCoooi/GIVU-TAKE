package com.accepted.givutake.funding.entity;

import com.accepted.givutake.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name="Funding_reviews")
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundingReviews extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_idx", nullable = false)
    private int reviewIdx;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "funding_idx", nullable = false)
    private Fundings fundings;

    @Column(name = "review_title", nullable = false, length = 50)
    private String reviewTitle;

    @Column(name = "review_content", nullable = false, length = 6000)
    private String reviewContent;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;
}
