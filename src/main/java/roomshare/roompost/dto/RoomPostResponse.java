package roomshare.roompost.dto;

import lombok.Getter;
import roomshare.roompost.entity.RoomPost;
import java.time.LocalDate;
import java.util.List;

@Getter
public class RoomPostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final Integer deposit;
    private final Integer monthlyRent;
    private final String location;
    private final LocalDate moveInDate;
    private final LocalDate contractEndDate;
    private final Long sellerId;
    private final String sellerEmail;
    private final List<RoomImageResponse> images;

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
        this.sellerEmail = roomPost.getSeller().getEmail();
        this.images = roomPost.getImages().stream().map(RoomImageResponse::new).toList();
    }
}