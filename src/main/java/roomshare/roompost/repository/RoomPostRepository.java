package roomshare.roompost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import roomshare.roompost.entity.RoomPost;

public interface RoomPostRepository extends JpaRepository<RoomPost, Long> {
}