package roomshare.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// CORS 관련 핵심 임포트 구문 추가
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import org.springframework.http.HttpMethod;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    // 암호화 기계
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티 규칙 설정 코드
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS 설정을 시큐리티 필터 체인에 적용....
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable) // REST API이므로 CSRF 보안 방패 비활성화
                .authorizeHttpRequests(auth -> auth
                        // 1. [추가] 매물 목록(GET) 및 상세 조회(GET)는 로그인 없이도 볼 수 있도록 허용!
                        .requestMatchers(HttpMethod.GET, "/api/room-posts/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/room-posts").permitAll()

                        // 2. 기존 허용 경로들
                        .requestMatchers("/api/users/join", "/api/users/login").permitAll()
                        .requestMatchers("/error").permitAll()

                        // 그 외의 모든 요청(POST 등록, 수정, 삭제 등)은 로그인 필요
                        .anyRequest().authenticated()
                )


                // 시큐리티의 기본 로그인 검사기보다 JwtFilter를 먼저 실행하라는 명령어
                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // CORS 설정 빈 추가
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 프론트엔드의 Live Server 주소 허용
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5500", "http://127.0.0.1:5500"));
        // 허용할 HTTP 메서드
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        // 허용할 헤더
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // 프론트엔드가 토큰이나 인증 정보를 함께 보낼 수 있도록 허용
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 API 경로(/**)에 위 설정 적용
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}