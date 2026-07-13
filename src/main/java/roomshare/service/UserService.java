package roomshare.service;

import roomshare.config.JwtUtil;
import roomshare.domain.User;
import roomshare.dto.LoginRequest;
import roomshare.dto.UserJoinRequest;
import roomshare.dto.UserResponse;
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
    private final JwtUtil jwtUtil;

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

    public String login(LoginRequest request) {
        // 1. 프론트가 보낸 이메일로 DB에서 유저 찾기 (없으면 예외 발생)
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        // 2. 비밀번호 일치 여부 확인 (스프링 시큐리티의 matches 기법 활용)
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 유저 이메일을 새긴 JWT 토큰을 발급해 반환
        return jwtUtil.createToken(user.getEmail());


    }
    // 기존 login 메서드 아래에 추가

    public UserResponse getMyInfo(String email) {
        // 1. 토큰에서 추출한 이메일로 DB에서 유저 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 2. 조회한 회원 정보를 안전하게 DTO로 변환하여 반환
        // (현재 User 엔티티에 nickname이나 profileImageUrl이 없다면 일단 가짜 데이터를 넣거나 필드를 조정하셔도 됩니다!)
        return new UserResponse(
                user.getEmail(),
                "김자취", // 임시로 넣어둔 닉네임 (추후 user.getNickname()으로 변경)
                "https://example.com/profile.png" // 임시 프로필 이미지
        );
    }
}