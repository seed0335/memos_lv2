package com.sparta.memos.service;

import com.sparta.memos.dto.SignupRequestDto;
import com.sparta.memos.entity.User;
import com.sparta.memos.jwtUtil.JwtUtil;
import com.sparta.memos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Service
public class UserService {//

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 회원가입
    public User signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
//            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
            return null;
        }

        // 사용자 등록
        User user = new User(username, password);
        userRepository.save(user);
        return user;
    }

}
