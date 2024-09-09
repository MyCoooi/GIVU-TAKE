package com.accepted.givutake.global.handler;

import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.global.model.ExceptionDto;
import com.querydsl.core.types.PathImpl;
import jakarta.persistence.Access;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.util.*;

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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDto> handleAccessDeniedException(AccessDeniedException exp) {
        log.error("AccessDeniedException 발생: message = {}", exp.getMessage());
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .code(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getCode())
                .message(exp.getMessage())
                .build();

        return new ResponseEntity<>(exceptionDto, ExceptionEnum.ACCESS_DENIED_EXCEPTION.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleValidationExceptions(MethodArgumentNotValidException exp) {
        // 여러 필드에서 유효성 검증이 실패했더라도, 첫 번째 오류에 대한 메세지만 반환
        String message = exp.getBindingResult().getFieldError().getDefaultMessage();

        log.error("MethodArgumentNotValidException 발생: message = {}", message);

        ExceptionDto exceptionDto = ExceptionDto.builder()
                .code(ExceptionEnum.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getCode())
                .message(message)
                .build();

        return new ResponseEntity<>(exceptionDto, ExceptionEnum.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionDto> handleTypeMismatch(MethodArgumentTypeMismatchException exp) {
        log.error("MethodArgumentTypeMismatchException 발생: message = {}", exp.getMessage());

        String message = exp.getValue() + "는 허용되지 않은 값입니다.";

        ExceptionDto exceptionDto = ExceptionDto.builder()
                .code(ExceptionEnum.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION.getCode())
                .message(message)
                .build();

        return new ResponseEntity<>(exceptionDto, ExceptionEnum.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionDto> handleConstraintViolationException(ConstraintViolationException exp) {
        log.error("ConstraintViolationException 발생: message = {}", exp.getMessage());

        // ConstraintViolation 리스트에서 첫 번째 항목 추출
        Set<ConstraintViolation<?>> violations = exp.getConstraintViolations();

        // 첫 번째 오류만 가져오기
        ConstraintViolation<?> firstViolation = violations.iterator().next();

        // 에러 메시지 추출
        String errorMessage = firstViolation.getMessage();

        ExceptionDto exceptionDto = ExceptionDto.builder()
                .code(ExceptionEnum.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION.getCode())
                .message(errorMessage)
                .build();

        return new ResponseEntity<>(exceptionDto, ExceptionEnum.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION.getStatus());
    }
}