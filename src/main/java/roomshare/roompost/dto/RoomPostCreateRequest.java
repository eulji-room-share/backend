package roomshare.roompost.dto;

import java.time.LocalDate;
import java.util.List;

public class RoomPostCreateRequest {

    private String title;
    private String content;
    private Integer deposit;
    private Integer monthlyRent;
    private String location;
    private LocalDate moveInDate;
    private LocalDate contractEndDate;

    private Long sellerId;

    private List<String> imageUrls;
    private List<String> originalFileNames;

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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public List<String> getOriginalFileNames() {
        return originalFileNames;
    }
}