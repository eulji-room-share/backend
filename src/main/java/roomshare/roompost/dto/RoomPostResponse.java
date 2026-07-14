package roomshare.roompost.dto;

import roomshare.roompost.entity.RoomPost;

import java.time.LocalDate;
import java.util.List;

public class RoomPostResponse {

    private Long id;
    private String title;
    private String content;
    private Integer deposit;
    private Integer monthlyRent;
    private String location;
    private LocalDate moveInDate;
    private LocalDate contractEndDate;
    private Long sellerId;
    private List<RoomImageResponse> images;

    public RoomPostResponse(RoomPost roomPost) {
        this.id = roomPost.getId();
        this.title = roomPost.getTitle();
        this.content = roomPost.getContent();
        this.deposit = roomPost.getDeposit();
        this.monthlyRent = roomPost.getMonthlyRent();
        this.location = roomPost.getLocation();
        this.moveInDate = roomPost.getMoveInDate();
        this.contractEndDate = roomPost.getContractEndDate();
        this.sellerId = roomPost.getSeller().getId();

        this.images = roomPost.getImages()
                .stream()
                .map(RoomImageResponse::new)
                .toList();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public Integer getMonthlyRent() {
        return monthlyRent;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getMoveInDate() {
        return moveInDate;
    }

    public LocalDate getContractEndDate() {
        return contractEndDate;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public List<RoomImageResponse> getImages() {
        return images;
    }
}