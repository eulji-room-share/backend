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
}