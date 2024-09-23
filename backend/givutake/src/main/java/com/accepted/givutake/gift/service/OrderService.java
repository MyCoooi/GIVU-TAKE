package com.accepted.givutake.gift.service;

import com.accepted.givutake.gift.entity.Gifts;
import com.accepted.givutake.gift.entity.Orders;
import com.accepted.givutake.gift.enumType.DeliveryStatus;
import com.accepted.givutake.gift.model.CreateOrderDto;
import com.accepted.givutake.gift.model.OrderDto;
import com.accepted.givutake.gift.model.UpdateOrderDto;
import com.accepted.givutake.gift.repository.GiftRepository;
import com.accepted.givutake.gift.repository.OrderRepository;
import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.user.common.entity.Users;
import com.accepted.givutake.user.common.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UsersRepository userRepository;
    private final GiftRepository giftRepository;

    public void createOrder(String email, CreateOrderDto request){
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_USER_WITH_EMAIL_EXCEPTION));
        Gifts gift = giftRepository.findById(request.getGiftIdx()).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_GIFT_EXCEPTION));
        Orders newOrder = Orders.builder()
                .users(user)
                .gift(gift)
                .paymentMethod(request.getPaymentMethod())
                .amount(request.getAmount())
                .price(request.getAmount()*gift.getPrice())
                .status(DeliveryStatus.PROCESSED)
                .build();
        orderRepository.save(newOrder);
    }

    public List<OrderDto> getOrdres(String email, int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));

        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_USER_WITH_EMAIL_EXCEPTION));

        Page<Orders> orderList = orderRepository.findByUsers(user, pageable);

        return orderList.map(order -> OrderDto.builder()
                .orderIdx(order.getOrderIdx())
                .userIdx(user.getUserIdx())
                .giftIdx(order.getGift().getGiftIdx())
                .giftName(order.getGift().getGiftName())
                .paymentMethod(order.getPaymentMethod())
                .amount(order.getAmount())
                .price(order.getPrice())
                .status(order.getStatus())
                .build()
        ).toList();
    }

    public OrderDto getOrder(String email, int orderIdx){
        Orders order = orderRepository.findById(orderIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ORDER_EXCEPTION));

        if(!order.getUsers().getEmail().equals(email)){
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }

        return OrderDto.builder()
                .orderIdx(order.getOrderIdx())
                .userIdx(order.getUsers().getUserIdx())
                .giftIdx(order.getGift().getGiftIdx())
                .giftName(order.getGift().getGiftName())
                .paymentMethod(order.getPaymentMethod())
                .amount(order.getAmount())
                .price(order.getPrice())
                .status(order.getStatus())
                .build();
    }

    public void updateOrder(String email, int orderIdx, UpdateOrderDto request){
        Orders order = orderRepository.findById(orderIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ORDER_EXCEPTION));

        if(!order.getGift().getCorporations().getEmail().equals(email)){ // 지자체만 배송업데이트 가능
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }

        order.setStatus(request.getStatus());
        orderRepository.save(order);
    }

}
