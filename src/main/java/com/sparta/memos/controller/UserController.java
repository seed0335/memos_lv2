package com.sparta.memos.controller;


import com.sparta.memos.dto.SignupRequestDto;
import com.sparta.memos.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
public class UserController {

    UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    //회원가입
    @PostMapping("/signup")
    public String signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        log.info("requestDto={}", requestDto);
        if(bindingResult.hasErrors()) {
            return "fail";
        }

        if(userService.signup(requestDto) == null) {
            return "fail";
        }

        return "Ok";
    }

    // 로그인 성공시 페이지 이동
    @GetMapping("/home")
    public String home() {
        return "ok";
    }

}
