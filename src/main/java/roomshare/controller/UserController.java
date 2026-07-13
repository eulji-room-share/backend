package roomshare.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import roomshare.dto.LoginRequest;
import roomshare.dto.UserJoinRequest;
import roomshare.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> joinUser(@RequestBody UserJoinRequest request) {
        userService.join(request);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다!");

    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            String message = userService.login(request);
            return ResponseEntity.ok(message); // 성공 시 200 OK와 함께 메시지 반환
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 실패 시 400 Bad Request와 에러 메시지 반환
        }
    }

    @GetMapping("/me")
    public ResponseEntity<String> getMyInfo(Authentication authentication) {
        // JwtFilter 통과한 사람만 이 코드를 실행할 수 있음...
        // authentication 안에는 '유저 이메일'이 들어있음...
        String email = authentication.getName();

        // 나중에는 여기서 이메일로 DB를 조회해서 닉네임, 프로필 사진 등을 응답하도록...
        // 지금은 토큰 검사가 잘 되는지 확인하기 위해 이메일만 반환..
        return ResponseEntity.ok("현재 안전하게 로그인된 사용자 이메일: " + email);
    }
}