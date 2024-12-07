package com.ict.practice.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ict.practice.common.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter{ // JwtRequestFilter 클래스가 요청당 한 번만 실행되도록 OncePerRequestFilter를 상속하자.
    @Autowired
    private JwtUtil jwTUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
        throws ServletException, IOException {

        log.info("필터 호출됨.");
        String id = null;
        String jwtToken  = null ;
        // 토큰 헤더 형식
        // Authorization: Bearer <JWT 토큰>
        final String tokenHeader = request.getHeader("Authoization"); // 요청 헤더에서 Authorizaion 값 확인

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) { // 요청 헤더가 Bearer 로 시작하는지 확인
            jwtToken = tokenHeader.substring(7);  // Bearer 제거하고 토큰만 추출
            log.info("헤더에서 토큰 추출함");
            
            // 토큰을 검증하러 JwtUtill로 가자.
            try { 
                id = jwTUtil.extractId(jwtToken);
                log.info("유틸로 토큰 검증 갔다옴");
                log.info("사용자 고유 식별키 id: " + id);


            } catch (Exception e) {
                logger.warn("토큰에러 발생");
            }
        }else {
            logger.warn("토큰이 존재하지 않음");
        }

        



        
    }  


}
