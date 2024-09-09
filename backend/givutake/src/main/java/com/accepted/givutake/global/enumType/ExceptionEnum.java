package com.accepted.givutake.global.enumType;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {
    // System Exception
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "ES0001"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.FORBIDDEN, "ES0002"),
    METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION(HttpStatus.BAD_REQUEST, "ES0003"),
    METHOD_ARGUMENT_NOT_VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "ES0004"),

    // Custom Exception
    ILLEGAL_REGION_EXCEPTION(HttpStatus.BAD_REQUEST, "EU0001", "지역 정보가 유효하지 않습니다."),
    ILLEGAL_ISMALE_EXCEPTION(HttpStatus.BAD_REQUEST, "EU0002", "성별 정보가 유효하지 않습니다."),
    ILLEGAL_BIRTH_EXCEPTION(HttpStatus.BAD_REQUEST, "EU0003", "생년월일 정보가 유효하지 않습니다."),
    ILLEGAL_STATUS_EXCEPTION(HttpStatus.BAD_REQUEST, "EU0004", "상태 정보가 유효하지 않습니다"),
    ILLEGAL_REPRESENTATIVE_ADDRESS_EXCEPTION(HttpStatus.BAD_REQUEST, "EU0005", "대표 주소 정보가 유효하지 않습니다."),
    ILLEGAL_REPRESENTATIVE_CARD_EXCEPTION(HttpStatus.BAD_REQUEST, "EU0006", "대표 카드 정보가 유효하지 않습니다."),
    ILLEGAL_EMAIL_EXCEPTION(HttpStatus.BAD_REQUEST, "EU0007", "이메일 형식이 올바르지 않습니다."),

    DUPLICATED_EMAIL_EXCEPTION(HttpStatus.BAD_REQUEST, "EU1006", "이미 사용 중인 이메일입니다."),

    UNEXPECTED_REGION_EXCEPTION(HttpStatus.BAD_REQUEST, "EU2001", "지역 정보는 포함할 수 없습니다."),
    UNEXPECTED_ISMALE_EXCEPTION(HttpStatus.BAD_REQUEST, "EU2002", "성별 정보는 포함할 수 없습니다."),
    UNEXPECTED_BIRTH_EXCEPTION(HttpStatus.BAD_REQUEST, "EU2003", "생년월일 정보는 포함할 수 없습니다."),
    UNEXPECTED_STATUS_EXCEPTION(HttpStatus.BAD_REQUEST, "EU2004", "상태 정보는 포함할 수 없습니다."),

    MISSING_REGION_EXCEPTION(HttpStatus.BAD_REQUEST, "EU3001", "지역 정보는 필수 입력 값 입니다."),
    MISSING_ISMALE_EXCEPTION(HttpStatus.BAD_REQUEST, "EU3002", "성별은 필수 입력 값 입니다."),
    MISSING_BIRTH_EXCEPTION(HttpStatus.BAD_REQUEST, "EU3003", "생년월일은 필수 입력 값 입니다."),
    MISSING_REPRESENTATIVE_ADDRESS_EXCEPTION(HttpStatus.BAD_REQUEST, "EU3005", "대표 주소는 필수 입력 값 입니다."),

    NOT_FOUND_USER_WITH_EMAIL_EXCEPTION(HttpStatus.NOT_FOUND, "EU4010", "해당 이메일을 찾을 수 없습니다."),

    UNEXPECTED_DATA_EXCEPTION(HttpStatus.FORBIDDEN, "EQ0001", "예상치 못한 값이 들어왔습니다."),

    NOT_FOUND_CATEGORY_EXCEPTION(HttpStatus.NOT_FOUND, "EC0001", "해당 카테고리를 찾을 수 없습니다."),

    NOT_FOUND_GIFT_EXCEPTION(HttpStatus.NOT_FOUND,"EG0001", "해당 답례품을 찾을 수 없습니다."),

    NOT_FOUND_GIFT_REVIEW_EXCEPTION(HttpStatus.NOT_FOUND,"EG1001","해당 답례품 후기를 찾을 수 없습니다."),

    NOT_FOUND_WISH_EXCEPTION(HttpStatus.NOT_FOUND,"EW0001", "해당 찜을 찾을 수 없습니다."),

    NOT_FOUND_SHOPPING_CART_EXCEPTION(HttpStatus.NOT_FOUND,"EP0001", "해당 장바구니를 찾을 수 없습니다."),

    NOT_FOUND_ORDER_EXCEPTION(HttpStatus.NOT_FOUND, "EO0001", "해당 주문을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}