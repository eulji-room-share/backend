package roomshare.roompost.entity;

import jakarta.persistence.*;
import roomshare.domain.User;

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

    // private Long sellerId; 대신 User 도메인과 연결..
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

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
            User seller // Long sellerId 대신 User 객체를 받도록 변경..
    ) {
        this.title = title;
        this.content = content;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.location = location;
        this.moveInDate = moveInDate;
        this.contractEndDate = contractEndDate;
        this.seller = seller; // 마찬가지로 변경
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

    // getSellerId() 메서드 삭제 후 교체
    public User getSeller() {
        return seller;
    }

    public List<RoomImage> getImages() {
        return images;
    }
}