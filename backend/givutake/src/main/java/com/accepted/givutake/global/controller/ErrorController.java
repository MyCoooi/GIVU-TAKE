package com.accepted.givutake.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // 에러가 발생했을 때 index.html로 포워딩
        return "forward:/index.html";
    }
}
