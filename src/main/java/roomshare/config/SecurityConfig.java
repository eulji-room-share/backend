package roomshare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 암호화 기계
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티 규칙 설정 코드
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 1. REST API이므로 CSRF 보안 방패 비활성화 (Postman 테스트를 위해 필수)
                .authorizeHttpRequests(auth -> auth
                        // 2. 회원가입과 로그인은 누구나(permitAll) 접근 가능하도록 명단에 추가
                        .requestMatchers("/api/users/join", "/api/users/login").permitAll()
                        // 3. 그 외의 모든 요청은 로그인(인증)을 해야만 접근 가능
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}