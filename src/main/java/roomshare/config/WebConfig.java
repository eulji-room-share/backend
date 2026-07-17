package roomshare.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 💡 SecurityConfig에서 이미 완벽하게 CORS를 세팅했으므로,
        // 여기서 이중으로 설정하지 않도록 모두 주석 처리(또는 삭제) 합니다!

        /*
        registry.addMapping("/**")
                .allowedOrigins( ... )
                ...
        */
    }
}