package com.sparta.memos.jwtUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.memos.dto.SignupRequestDto;
import com.sparta.memos.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j(topic = "로그인 및 JWT 생성")

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {//

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/users/login");
        //상속받으면 사용할 수 있는 메소드 : 로그인 페이지를 직접 설정함

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 로그인을 시도하는 메소드
        log.info("로그인 시도");




        log.info("request={}", request);
        try {
            SignupRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), SignupRequestDto.class);
            // json 형식을 ObjectMapper 읽어서 객체로 변환함 첫번째는 받는 값, 두번째는 변환한 타입
//            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);


            return getAuthenticationManager().authenticate( // 매니저를 사용해서, authenticate 메소드를 사용함 검증 역할 수행
                    new UsernamePasswordAuthenticationToken( //토큰을 만들고, 유저네임, 비밀번호, 권한은 없음으로 넣음
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    // 로그인이 성공했을 때 나오는 메시지
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //@Authentication authResult 에 객체가 들어 있다.
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();

        //역할은 생략함
        log.info("로그인 성공, 토큰 생성");
        String token = jwtUtil.createToken(username); // 토근 생성
        jwtUtil.addJwtToCookie(token, response); // 쿠키에 토근 넣기
        response.sendRedirect(request.getContextPath() + "/home");
    }

    // 로그인 실패했을 때 실행
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(401);
    }
}
