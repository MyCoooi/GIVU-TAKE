package com.accepted.givutake.global.enumType;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;

@Getter
@ToString
public enum ExceptionEnum {
    // System Exception
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "ES0001", "실행 중 오류가 발생했습니다."),
    ACCESS_DENIED_EXCEPTION(HttpStatus.FORBIDDEN, "ES0002"),
    METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION(HttpStatus.BAD_REQUEST, "ES0003"),
    METHOD_ARGUMENT_NOT_VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "ES0004"),
    SECURITY_EXCEPTION(HttpStatus.UNAUTHORIZED, "ES0005", "보안 위반으로 인해 토큰 처리에 실패했습니다."),
    EXPIRED_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "ES0006", "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "ES0007", "지원하지 않는 토큰입니다."),
    MALFORMED_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "ES0008", "잘못된 토큰 형식입니다."),
    INVALID_SIGNATURE_EXCEPTION(HttpStatus.UNAUTHORIZED, "ES0009", "유효하지 않은 토큰 서명입니다."),
    INVALID_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "ES0010", "유효하지 않은 토큰입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.UNAUTHORIZED, "ES0011", "서버에 문제가 발생했습니다."),
    MESSAGING_EXCEPTION(HttpStatus.BAD_REQUEST, "ES0012", "이메일 전송에 실패했습니다."),
    HTTP_MESSAGE_NOT_READABLE_EXCEPTION(HttpStatus.BAD_REQUEST, "ES0013", "요청 본문을 처리할 수 없습니다."),
    BAD_CREDENTIALS_EXCEPTION(HttpStatus.BAD_REQUEST, "ES0014", "아이디나 비밀번호가 일치하지 않습니다."),
    INTERNAL_AUTHENTICATION_SERVICE_EXCEPTION(HttpStatus.BAD_REQUEST, "ES0015"),

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
    UNEXPECTED_REPRESENTATIVE_ADDRESS_EXCEPTION(HttpStatus.BAD_REQUEST, "EU2005", "대표 주소 정보는 포함할 수 없습니다."),

    MISSING_REGION_EXCEPTION(HttpStatus.BAD_REQUEST, "EU3001", "지역 정보는 필수 입력 값 입니다."),
    MISSING_ISMALE_EXCEPTION(HttpStatus.BAD_REQUEST, "EU3002", "성별은 필수 입력 값 입니다."),
    MISSING_BIRTH_EXCEPTION(HttpStatus.BAD_REQUEST, "EU3003", "생년월일은 필수 입력 값 입니다."),
    MISSING_REPRESENTATIVE_ADDRESS_EXCEPTION(HttpStatus.BAD_REQUEST, "EU3005", "대표 주소는 필수 입력 값 입니다."),

    NOT_FOUND_USER_WITH_EMAIL_EXCEPTION(HttpStatus.NOT_FOUND, "EU4010", "해당 이메일을 가진 사용자를 찾을 수 없습니다."),
    NOT_FOUND_REFRESHTOKEN_EXCEPTION(HttpStatus.NOT_FOUND, "EU4012", "토큰 정보를 찾을 수 없습니다."),
    NOT_FOUND_EMAILCODE_EXCEPTION(HttpStatus.NOT_FOUND, "EU4013", "인증 코드 정보를 찾을 수 없습니다."),
    NOT_FOUND_ADDRESSES_EXCEPTION(HttpStatus.NOT_FOUND, "EU4020", "주소 정보를 찾을 수 없습니다."),
    NOT_FOUND_FUNDING_REVIEW_EXCEPTION(HttpStatus.NOT_FOUND, "EU4030", "펀딩 후기 정보를 찾을 수 없습니다."),
    NOT_FOUND_FUNDING_WITH_IDX_EXCEPTION(HttpStatus.NOT_FOUND, "EU4031", "펀딩 정보를 찾을 수 없습니다."),
    NOT_FOUND_CHEER_COMMENT_EXCEPTION(HttpStatus.NOT_FOUND, "EU4040", "댓글 정보를 찾을 수 없습니다."),

    USER_ALREADY_WITHDRAWN_EXCEPTION(HttpStatus.NOT_FOUND, "EU5001", "이미 탈퇴한 회원입니다."),
    ADDRESSES_ALREADY_DELETED_EXCEPTION(HttpStatus.NOT_FOUND, "EU5020", "이미 삭제된 주소입니다."),
    FUNDING_REVIEWS_ALREADY_DELETED_EXCEPTION(HttpStatus.NOT_FOUND, "EU5030", "이미 삭제된 펀딩 후기 입니다."),
    FUNDING_ALREADY_DELETED_EXCEPTION(HttpStatus.NOT_FOUND, "EU5031", "이미 삭제된 펀딩 입니다."),
    CHEER_COMMENT_ALREADY_DELETED_EXCEPTION(HttpStatus.NOT_FOUND, "EU5040", "이미 삭제된 댓글 입니다."),

    PASSWORD_MISMATCH_EXCEPTION(HttpStatus.BAD_REQUEST, "EU6011", "비밀번호가 일치하지 않습니다."),
    REFRESHTOKEN_MISMATCH_EXCEPTION(HttpStatus.BAD_REQUEST, "EU6012", "토큰 정보가 일치하지 않습니다."),
    EMAILCODE_MISMATCH_EXCEPTION(HttpStatus.BAD_REQUEST, "EU6013", "인증 코드가 일치하지 않습니다."),

    NOT_ALLOWED_LAST_ADDRESS_DELETION_EXCEPTION(HttpStatus.BAD_REQUEST, "EU7020", "마지막 주소 정보는 삭제할 수 없습니다."),
    NOT_ALLOWED_FUNDING_REVIEW_INSERTION_EXCEPTION(HttpStatus.BAD_REQUEST, "EU7030", "이미 펀딩 후기가 작성 되어 있습니다."),
    NOT_ALLOWED_WISH_INSERTION_EXCEPTION(HttpStatus.BAD_REQUEST, "EW7030","이미 찜목록에 추가 되어 있습니다."),

    NOT_FOUND_CATEGORY_EXCEPTION(HttpStatus.NOT_FOUND, "EC0001", "해당 카테고리를 찾을 수 없습니다."),
    NOT_FOUND_GIFT_EXCEPTION(HttpStatus.NOT_FOUND,"EG0001", "해당 답례품을 찾을 수 없습니다."),
    NOT_FOUND_GIFT_REVIEW_EXCEPTION(HttpStatus.NOT_FOUND,"EG1001","해당 답례품 후기를 찾을 수 없습니다."),
    NOT_FOUND_WISH_EXCEPTION(HttpStatus.NOT_FOUND,"EW0001", "해당 찜을 찾을 수 없습니다."),
    NOT_FOUND_SHOPPING_CART_EXCEPTION(HttpStatus.NOT_FOUND,"EP0001", "해당 장바구니를 찾을 수 없습니다."),
    NOT_FOUND_ORDER_EXCEPTION(HttpStatus.NOT_FOUND, "EO0001", "해당 주문을 찾을 수 없습니다."),
    NOT_FOUND_QNA_EXCEPTION(HttpStatus.NOT_FOUND,"EN0001", "해당 Q&A를 찾을 수 없습니다."),
    NOT_FOUND_QNA_ANSWER_EXCEPTION(HttpStatus.NOT_FOUND,"EN0002", "해당 Q&A의 답변을 찾을 수 없습니다"),

    UNEXPECTED_DATA_EXCEPTION(HttpStatus.FORBIDDEN, "EQ0001", "예상치 못한 값이 들어왔습니다.");

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