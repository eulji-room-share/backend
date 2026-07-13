package roomshare.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. 프론트엔드가 보낸 요청 헤더에서 "Authorization" 부분(토큰이 담긴 곳)을 꺼냄....
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 2. 토큰이 없거나, "Bearer "로 시작하지 않으면 통과시키지 않고 다음으로 넘김... (로그인 안 한 상태)
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. "Bearer " 글자를 떼어내고 진짜 순수 토큰 문자열만 추출....
        String token = authorization.split(" ")[1];

        // 4. JwtUtil의 토큰 만료 검증 메서드를 통과했는지 확인
        if (jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 5. 토큰 안에서 유저 이메일을 뽑아냄..
        String email = jwtUtil.getEmail(token);

        // 6. 시큐리티 문지기에게 임시 출입증을 줌..
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, null); // 원래는 권한(Role)이 들어가지만 지금은 생략
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 7. 무사히 다음 로직(컨트롤러)으로 이동
        filterChain.doFilter(request, response);
    }
}