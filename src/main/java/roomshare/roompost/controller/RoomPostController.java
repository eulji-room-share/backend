package roomshare.roompost.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication; // 1. 시큐리티 Authentication 임포트 추가
import org.springframework.web.bind.annotation.*;
import roomshare.roompost.dto.RoomPostCreateRequest;
import roomshare.roompost.dto.RoomPostResponse;
import roomshare.roompost.dto.RoomPostUpdateRequest;
import roomshare.roompost.service.RoomPostService;

import java.util.List;

@RestController
@RequestMapping("/api/room-posts")
public class RoomPostController {

    private final RoomPostService roomPostService;

    public RoomPostController(RoomPostService roomPostService) {
        this.roomPostService = roomPostService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomPostResponse createRoomPost(
            @RequestBody RoomPostCreateRequest request,
            Authentication authentication // 2. 매개변수에 Authentication 추가 (로그인 유저 정보 가로챔...)
    ) {
        // 3. 인증 객체에서 유저의 이메일(또는 ID)을 꺼냄....
        String email = authentication.getName();

        // 4. 서비스 레이어로 요청 데이터와 이메일을 함께 넘겨줌...
        return roomPostService.createRoomPost(request, email);
    }

    @GetMapping
    public List<RoomPostResponse> getRoomPosts() {
        return roomPostService.getRoomPosts();
    }

    @GetMapping("/{id}")
    public RoomPostResponse getRoomPost(@PathVariable Long id) {
        return roomPostService.getRoomPost(id);
    }

    // 1. 수정(PATCH) 컨트롤러: Authentication 추가 및 email 전달
    @PatchMapping("/{id}")
    public RoomPostResponse updateRoomPost(
            @PathVariable Long id,
            @RequestBody RoomPostUpdateRequest request,
            Authentication authentication // 로그인 유저 정보 가져오기
    ) {
        String email = authentication.getName(); // 토큰에서 이메일 추출
        return roomPostService.updateRoomPost(id, request, email); // 서비스로 이메일 전달...
    }

    // 2. 삭제(DELETE) 컨트롤러: Authentication 추가 및 email 전달
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoomPost(
            @PathVariable Long id,
            Authentication authentication // 로그인 유저 정보 가져오기
    ) {
        String email = authentication.getName(); // 토큰에서 이메일 추출
        roomPostService.deleteRoomPost(id, email); // 서비스로 이메일 전달..
    }
}