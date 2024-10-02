package com.accepted.givutake.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ForwardController {

    @RequestMapping(value = "/**")
    public String forward(HttpServletRequest request) {
        String path = request.getRequestURI();

        // index.html 요청은 즉시 반환
        if ("/index.html".equals(path)) {
            return "forward:/index.html";
        }

        // API 요청은 포워딩하지 않음
        if (path.startsWith("/api/")) {
            return null;  // Spring이 기존 핸들러를 찾도록 함
        }

        // 정적 리소스 요청도 포워딩하지 않음
        if (path.matches(".*\\.(css|js|jpg|png|gif|ico)$")) {
            return null;
        }

        // 그 외의 모든 요청은 index.html로 포워딩
        return "forward:/index.html";
    }
}