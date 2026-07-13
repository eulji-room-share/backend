package roomshare.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key;
    private final long accessTokenExpTime = 1000 * 60 * 60; // 토큰 유효시간: 1시간

    // application.yml에 적어둔 비밀키를 가져와서 진짜 암호화 키로 벼려내는 작업
    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = secretKey.getBytes();
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 이메일을 받아서 1시간짜리 출입증(토큰)을 발급해주는 메서드
    public String createToken(String email) {
        return Jwts.builder()
                .setSubject(email) // 토큰 몸통에 이메일 새겨넣기
                .setIssuedAt(new Date()) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpTime)) // 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // 비밀키로 위조 방지 서명
                .compact(); // 압축해서 문자열로 반환
    }
}
