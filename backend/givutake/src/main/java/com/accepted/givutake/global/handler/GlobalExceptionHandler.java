package com.accepted.givutake.global.handler;

import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.global.model.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 에러코드를 통한 API 관련 exception 처리
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionDto> handleApiException(ApiException exp) {
        log.error("handleApiException 발생: code = {}", exp.getError().getCode());
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .code(exp.getError().getCode())
                .message(exp.getError().getMessage())
                .build();

        return new ResponseEntity<>(exceptionDto, exp.getError().getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDto> handleRuntimeException(RuntimeException exp) {
        log.error("RuntimeException 발생: message = {}", exp.getMessage());
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .code(ExceptionEnum.RUNTIME_EXCEPTION.getCode())
                .message(exp.getMessage())
                .build();

        return new ResponseEntity<>(exceptionDto, ExceptionEnum.RUNTIME_EXCEPTION.getStatus());
    }

}