package com.ict.practice.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ict.practice.jwt.JwtRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecurityConfig {
    private JwtRequestFilter filter;
    
     // 서버에 들어오는 모든 요청은 SecurityFilterchain을 거친다.
    // addFilterBefore때문에 JwtRequestFilter가 먼저 실행된다.
    @Bean
    public SecurityFilterChain chain(HttpSecurity http) throws Exception{ // HttpSecurity는 Spring Security에서 HTTP 요청에 대한 보안을 설정하는 객체
        log.info("필터체인 호출됨");
        // CORS(Cross-Origin Resource Sharing): 서로 다른 도메인 간의 리소스 공유를 제어하는 보안 메커니즘
        // CSRF(Cross-Site Request Forgery)**는 사용자가 의도하지 않은 요청을 보내는 공격
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))  // CORS 설정의 출처를 지정하는 역할
        .csrf(csrf -> csrf.disable()) // CSRF 보호 기능을 비활성화
        
        // HTTP요청에 대한 권한 설정 
        .authorizeHttpRequests(authorize -> 
        authorize.requestMatchers("/**").permitAll() // 해당 요청에 대한 접근 허용(지금상태는 모두허용)
        .anyRequest().authenticated()) // 나머지 요청들은 인증을 요구
        .addFilterBefore(filter,  UsernamePasswordAuthenticationFilter.class); // jwtRequestFilter가 뒤에 오는필터보다 먼저 실행
        
        return http.build(); // 위에서 여러 보안설정을 추가한 후 최종적으로 chain 생성.
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() { // chain에 있는  메소드 선언.
        CorsConfiguration config = new CorsConfiguration(); // CorsConfiguration 객체생성

        // "*"는 전부 허용한다는 의미이다.
        config.setAllowedOrigins(Arrays.asList("*")); // 어떤 출처을 허용할지를 지정
        config.setAllowedMethods(Arrays.asList("*")); //   어떤 HTTP 메서드(GET, POST, PUT, DELETE, PATCH)를 허용할 것인지를 지정
        config.setAllowedHeaders(Arrays.asList("*"));  // 어떤 HTTP 헤더를 허용할 것인지 지정
        config.setAllowCredentials(true); //  클라이언트에서 서버로 쿠기나 인증 정보를 함께 보내려면 이 설정이 true여야 한다.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // =>  URL 패턴을 기준으로 여러 CORS 설정을 관리할 수 있도록 도와주는 클래스 
        source.registerCorsConfiguration("/**", config); // config에 담겨있는 조건들을  모든 경로에 적용
        return source; // 위의 설정들을 반환한다.
    }
    
    public SecurityConfig(JwtRequestFilter filter){ // 생성자
        log.info("시큐리티 컨피그 메소드 호출됨.");
        this.filter = filter;

    }



    
}
