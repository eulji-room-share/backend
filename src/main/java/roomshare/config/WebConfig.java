package roomshare.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 API 경로에 대해 CORS 설정 적용...
                .allowedOrigins(
                        "http://localhost:3000", // 로컬 React 개발 서버 기본 주소
                        "http://localhost:5173"  // 로컬 Vite/React 개발 서버 기본 주소
                )
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true) // 쿠키나 인증 헤더를 허용하는 설정
                .maxAge(3600); // 프리플라이트(Preflight) 요청을 캐싱할 시간 (1시간)
    }
}