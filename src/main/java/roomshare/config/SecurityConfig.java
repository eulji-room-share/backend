package roomshare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 비밀번호를 암호화해주는 기계(BCrypt)를 스프링에 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티 세부 규칙 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 개발 초기 테스트를 위해 csrf 보호를 잠시 꺼둠...
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/join").permitAll() // 회원가입 주소는 로그인 없이도 누구나 접근 가능..
                        .anyRequest().authenticated() // 그 외의 모든 요청은 로그인해야만 접근 가능...
                );

        return http.build();
    }
}