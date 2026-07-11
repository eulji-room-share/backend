package roomshare.roompost.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RoomPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer deposit;

    private Integer monthlyRent;

    private String location;

    private LocalDate moveInDate;

    private LocalDate contractEndDate;

    private Long sellerId;

    @OneToMany(mappedBy = "roomPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomImage> images = new ArrayList<>();

    protected RoomPost() {
    }

    public RoomPost(
            String title,
            String content,
            Integer deposit,
            Integer monthlyRent,
            String location,
            LocalDate moveInDate,
            LocalDate contractEndDate,
            Long sellerId
    ) {
        this.title = title;
        this.content = content;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.location = location;
        this.moveInDate = moveInDate;
        this.contractEndDate = contractEndDate;
        this.sellerId = sellerId;
    }

    public void update(
            String title,
            String content,
            Integer deposit,
            Integer monthlyRent,
            String location,
            LocalDate moveInDate,
            LocalDate contractEndDate
    ) {
        this.title = title;
        this.content = content;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.location = location;
        this.moveInDate = moveInDate;
        this.contractEndDate = contractEndDate;
    }

    public void addImage(RoomImage image) {
        images.add(image);
        image.setRoomPost(this);
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

    public List<RoomImage> getImages() {
        return images;
    }
}