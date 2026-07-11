package roomshare.roompost.dto;

import java.time.LocalDate;

public class RoomPostUpdateRequest {

    private String title;
    private String content;
    private Integer deposit;
    private Integer monthlyRent;
    private String location;
    private LocalDate moveInDate;
    private LocalDate contractEndDate;

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
}