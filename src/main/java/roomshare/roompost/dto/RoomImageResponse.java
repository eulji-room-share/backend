package roomshare.roompost.dto;

import roomshare.roompost.entity.RoomImage;

public class RoomImageResponse {

    private Long id;
    private String imageUrl;
    private String originalFileName;

    public RoomImageResponse(RoomImage image) {
        this.id = image.getId();
        this.imageUrl = image.getImageUrl();
        this.originalFileName = image.getOriginalFileName();
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
}