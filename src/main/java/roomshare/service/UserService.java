package roomshare.service;

import roomshare.domain.User;
import roomshare.dto.UserJoinRequest;
import roomshare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // 암호화 기계 주입

    @Transactional
    public void join(UserJoinRequest request) {
        // 1. 이메일 중복 검사
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 2. 비밀번호 암호화 진행 (비밀번호 -> 식별불가한 해시값)
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 3. DTO에 담긴 데이터와 '암호화된 비밀번호'로 새 User 엔티티 생성
        User user = User.builder()
                .email(request.getEmail())
                .password(encodedPassword) // 암호화된 비밀번호가 들어감..
                .nickname(request.getNickname())
                .build();

        // 4. DB에 저장
        userRepository.save(user);
    }
}