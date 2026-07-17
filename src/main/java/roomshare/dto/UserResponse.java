package roomshare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private String email;
    private String nickname;
    private String profileImageUrl;
}