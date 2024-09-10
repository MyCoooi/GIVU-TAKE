package com.accepted.givutake.global.exception;

import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.model.ExceptionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CustomErrorSend {
    public static void handleException(HttpServletResponse response, ExceptionEnum exceptionEnum) throws IOException {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .code(exceptionEnum.getCode())
                .message(exceptionEnum.getMessage())
                .build();

        response.setStatus(exceptionEnum.getStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionDto));
    }
}