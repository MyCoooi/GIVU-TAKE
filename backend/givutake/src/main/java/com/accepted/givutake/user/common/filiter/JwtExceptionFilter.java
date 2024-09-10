package com.accepted.givutake.user.common.filiter;

import ch.qos.logback.core.status.ErrorStatus;
import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.CustomErrorSend;
import com.accepted.givutake.global.exception.JwtAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException authException) {
            String errorName = authException.getMessage();
            ExceptionEnum exceptionEnum = ExceptionEnum.valueOf(errorName);

            CustomErrorSend.handleException(response, exceptionEnum);
        }
    }
}