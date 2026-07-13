package roomshare.controller;

import roomshare.dto.UserJoinRequest;
import roomshare.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}