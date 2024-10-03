package com.accepted.givutake.user.client.service;

import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.user.client.entity.Addresses;
import com.accepted.givutake.user.client.entity.Cards;
import com.accepted.givutake.user.client.repository.CardsRepository;
import com.accepted.givutake.user.common.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CardService {

    private final CardsRepository cardsRepository;

    // 카드 번호가 동일한 카드가 존재하는지 확인
    public boolean isExistCardByCardNumber(String cardNumber) {
        return cardsRepository.existsByCardNumber(cardNumber);
    }

    // 이전의 대표 카드를 false 처리
    public void updateRepresentativeCardFalse(Users users) {
        Optional<Cards> optionalRepresentativeCard = cardsRepository.findByUsersAndIsRepresentativeTrue(users);

        if (optionalRepresentativeCard.isPresent()) {
            Cards representativeCard = optionalRepresentativeCard.get();
            representativeCard.setRepresentative(false);
            cardsRepository.save(representativeCard);
        }
    }

    // DB에 카드 등록
    public Cards saveCard(Cards cards) {
        if (this.isExistCardByCardNumber(cards.getCardNumber())) {
            throw new ApiException(ExceptionEnum.DUPLICATED_CARD_EXCEPTION);
        }

        return cardsRepository.save(cards);
    }
}
