package com.accepted.givutake.user.client.model;

import com.accepted.givutake.user.client.entity.Cards;
import com.accepted.givutake.user.common.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private int cardIdx;
    private Users users;
    private String cardCompany;
    private String cardNumber;
    private String cardCVC;
    private LocalDate cardExpiredDate;
    private boolean isRepresentative;
    private boolean isDeleted;

    public static CardDto toDto(Cards cards) {
        return CardDto.builder()
                .cardIdx(cards.getCardIdx())
                .users(cards.getUsers())
                .cardCompany(cards.getCardCompany())
                .cardNumber(cards.getCardNumber())
                .cardCVC(cards.getCardCVC())
                .cardExpiredDate(cards.getCardExpiredDate())
                .isRepresentative(cards.isRepresentative())
                .isDeleted(cards.isDeleted())
                .build();
    }

    public CardViewDto toCardViewDto() {
        return CardViewDto.builder()
                .cardIdx(this.cardIdx)
                .cardCompany(this.cardCompany)
                .cardNumber(this.cardNumber)
                .cardCVC(this.cardCVC)
                .cardExpiredDate(this.cardExpiredDate)
                .isRepresentative(this.isRepresentative)
                .build();
    }
}
