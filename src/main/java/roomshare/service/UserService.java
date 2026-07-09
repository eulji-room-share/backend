package roomshare.service;


import roomshare.domain.User;
import roomshare.dto.UserJoinRequest;
import roomshare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void join(UserJoinRequest request) {
        // 1. 이메일 중복 검사
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 가입한 이메일입니다.");
        }

        // 2. DTO에 담긴 데이터로 새로운 User 엔티티 생성
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword()) // (비밀번호 암호화 추가해도 좋을 듯...)
                .nickname(request.getNickname())
                .build();

        // 3. DB에 저장
        userRepository.save(user);
    }
}