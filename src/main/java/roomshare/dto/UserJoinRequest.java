package roomshare.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinRequest {
    private String email;
    private String password;
    private String nickname;
}
