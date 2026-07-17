package roomshare.roompost.entity;

import jakarta.persistence.*;

@Entity
public class RoomImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private String originalFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_post_id")
    private RoomPost roomPost;

    protected RoomImage() {
    }

    public RoomImage(String imageUrl, String originalFileName) {
        this.imageUrl = imageUrl;
        this.originalFileName = originalFileName;
    }

    public void setRoomPost(RoomPost roomPost) {
        this.roomPost = roomPost;
    }

    public Long getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public RoomPost getRoomPost() {
        return roomPost;
    }
}