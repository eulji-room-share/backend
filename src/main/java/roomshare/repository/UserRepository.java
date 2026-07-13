package roomshare.repository;

import roomshare.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일이 이미 DB에 존재하는지(중복 가입인지) 확인하는 메서드
    boolean existsByEmail(String email);
}
