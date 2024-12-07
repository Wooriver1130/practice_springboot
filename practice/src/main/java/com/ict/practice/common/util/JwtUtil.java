package com.ict.practice.common.util;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    // 어노테이션 @Value 는 파일(application.yml, application.properties, 또는 .env 파일 등)에서 값을 가져와 필드에 주입 할 수 있다.
    @Value("${jwt.secret}") 
    private String secret; // 시크릿 키
    @Value("${jwt.expiration}")
    private long expiration; // 만료 시간

    // Claims는 JWT토큰의 구성중 하나인 payload의 내용물을 말한다.
    public String extractId(String token){  
        // subject는 사용자를 식별하는 고유 id를 말한다.
        return extractClaim(token, Claims::getSubject); //  :: (메서드 참조) => Claims객체의 getSubject 메소드를 인자로 넘김
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { // claimsResolver는 넘겨받은 함수형 인터페이스를 가지고 있는 객체.
        final Claims claims = extractALLClaim(token);
        return claimsResolver.apply(claims); // claimsResolver(claims) ❌ (자바에서는 지원되지 않음)
    }
    public Claims extractALLClaim(String token){
        return Jwts.parserBuilder() // 빌더(JWT파싱하는놈) 생성
                .setSigningKey(getKey()) // 서버의 비밀키 를 가져와서 Sign설정
                .build() 
                .parseClaimsJws(token) // 토큰과 서버의 Sign이 일치한지 확인하고 일치하면   jwt토큰에서 payload(Claims) 부분 추출
                .getBody();  // 반환
    }

    private SecretKey getKey(){
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String id) {
        Map
    }


}
