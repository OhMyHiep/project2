package domain.request;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
}
