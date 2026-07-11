package roomshare.roompost.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomshare.roompost.dto.RoomPostCreateRequest;
import roomshare.roompost.dto.RoomPostResponse;
import roomshare.roompost.dto.RoomPostUpdateRequest;
import roomshare.roompost.entity.RoomImage;
import roomshare.roompost.entity.RoomPost;
import roomshare.roompost.repository.RoomPostRepository;

import java.util.List;

@Service
@Transactional
public class RoomPostService {

    private final RoomPostRepository roomPostRepository;

    public RoomPostService(RoomPostRepository roomPostRepository) {
        this.roomPostRepository = roomPostRepository;
    }

    public RoomPostResponse createRoomPost(RoomPostCreateRequest request) {
        RoomPost roomPost = new RoomPost(
                request.getTitle(),
                request.getContent(),
                request.getDeposit(),
                request.getMonthlyRent(),
                request.getLocation(),
                request.getMoveInDate(),
                request.getContractEndDate(),
                request.getSellerId()
        );

        if (request.getImageUrls() != null) {
            for (int i = 0; i < request.getImageUrls().size(); i++) {
                String imageUrl = request.getImageUrls().get(i);

                String originalFileName = null;
                if (request.getOriginalFileNames() != null && i < request.getOriginalFileNames().size()) {
                    originalFileName = request.getOriginalFileNames().get(i);
                }

                RoomImage image = new RoomImage(imageUrl, originalFileName);
                roomPost.addImage(image);
            }
        }

        RoomPost savedRoomPost = roomPostRepository.save(roomPost);

        return new RoomPostResponse(savedRoomPost);
    }

    @Transactional(readOnly = true)
    public List<RoomPostResponse> getRoomPosts() {
        return roomPostRepository.findAll()
                .stream()
                .map(RoomPostResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoomPostResponse getRoomPost(Long id) {
        RoomPost roomPost = roomPostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + id));

        return new RoomPostResponse(roomPost);
    }

    public RoomPostResponse updateRoomPost(Long id, RoomPostUpdateRequest request) {
        RoomPost roomPost = roomPostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + id));

        roomPost.update(
                request.getTitle(),
                request.getContent(),
                request.getDeposit(),
                request.getMonthlyRent(),
                request.getLocation(),
                request.getMoveInDate(),
                request.getContractEndDate()
        );

        return new RoomPostResponse(roomPost);
    }

    public void deleteRoomPost(Long id) {
        RoomPost roomPost = roomPostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + id));

        roomPostRepository.delete(roomPost);
    }
}