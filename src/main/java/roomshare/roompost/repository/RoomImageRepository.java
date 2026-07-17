package roomshare.roompost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import roomshare.roompost.entity.RoomImage;

public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {
}