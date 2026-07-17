package roomshare.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import roomshare.dto.LoginRequest;
import roomshare.dto.UserJoinRequest;
import roomshare.dto.UserResponse;
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
    public ResponseEntity<UserResponse> getMyInfo(Authentication authentication) {
        String email = authentication.getName();

        // 서비스를 통해 유저 정보 DTO를 가져옴....
        UserResponse userResponse = userService.getMyInfo(email);

        // 200 OK와 함께 회원 정보 객체를 반환..(JSON)
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // JWT 토큰 방식은 서버가 세션을 유지하지 않는 무상태(Stateless) 방식이므로,
        // 로그아웃의 핵심은 프론트엔드가 보관 중인 토큰을 브라우저에서 지우는 것...
        // 백엔드에서는 요청을 무사히 받았다는 성공 메시지만 반환....
        return ResponseEntity.ok("성공적으로 로그아웃 되었습니다.");
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteAccount(Authentication authentication) {
        // 1. 토큰에서 로그인된 사용자의 이메일을 꺼냄..
        String email = authentication.getName();

        // 2. 서비스 로직을 호출하여 DB에서 삭제...
        userService.deleteAccount(email);

        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다. 이용해 주셔서 감사합니다.");
    }
}