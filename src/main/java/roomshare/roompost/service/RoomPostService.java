package roomshare.roompost.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomshare.domain.User; // User 엔티티가 있는 패키지로 import 경로 맞추기...
import roomshare.repository.UserRepository; // UserRepository가 있는 패키지로 import 경로 맞추기...
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
    private final UserRepository userRepository; // 1. UserRepository 추가 주입

    //  2. 생성자에 UserRepository 추가
    public RoomPostService(RoomPostRepository roomPostRepository, UserRepository userRepository) {
        this.roomPostRepository = roomPostRepository;
        this.userRepository = userRepository;
    }

    // 3. 로그인한 사용자의 email을 매개변수로 함께 받음...
    public RoomPostResponse createRoomPost(RoomPostCreateRequest request, String email) {

        // 4. 토큰에서 추출한 이메일로 실제 가입된 유저 정보를 조회...
        User seller = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. email=" + email));

        // 5. 생성자에 request.getSellerId() 대신 조회한 User 객체(seller)를 주입...
        RoomPost roomPost = new RoomPost(
                request.getTitle(),
                request.getContent(),
                request.getDeposit(),
                request.getMonthlyRent(),
                request.getLocation(),
                request.getMoveInDate(),
                request.getContractEndDate(),
                seller // 변경..
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

    // 1. 수정 메서드: email 파라미터 추가 및 권한 검증 로직 추가
    public RoomPostResponse updateRoomPost(Long id, RoomPostUpdateRequest request, String email) {
        RoomPost roomPost = roomPostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + id));

        // 게시글 작성자의 이메일과 요청자의 이메일이 다르면 예외 발생...
        if (!roomPost.getSeller().getEmail().equals(email)) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 수정할 수 있습니다.");
        }

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

    // 2. 삭제 메서드: email 파라미터 추가 및 권한 검증 로직 추가
    public void deleteRoomPost(Long id, String email) {
        RoomPost roomPost = roomPostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + id));

        // 본인이 아니면 삭제 불가능..
        if (!roomPost.getSeller().getEmail().equals(email)) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 삭제할 수 있습니다.");
        }

        roomPostRepository.delete(roomPost);
    }
}