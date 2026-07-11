package roomshare.roompost.controller;

import org.springframework.http.HttpStatus;
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
    public RoomPostResponse createRoomPost(@RequestBody RoomPostCreateRequest request) {
        return roomPostService.createRoomPost(request);
    }

    @GetMapping
    public List<RoomPostResponse> getRoomPosts() {
        return roomPostService.getRoomPosts();
    }

    @GetMapping("/{id}")
    public RoomPostResponse getRoomPost(@PathVariable Long id) {
        return roomPostService.getRoomPost(id);
    }

    @PatchMapping("/{id}")
    public RoomPostResponse updateRoomPost(
            @PathVariable Long id,
            @RequestBody RoomPostUpdateRequest request
    ) {
        return roomPostService.updateRoomPost(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoomPost(@PathVariable Long id) {
        roomPostService.deleteRoomPost(id);
    }
}