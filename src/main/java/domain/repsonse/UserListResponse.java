package domain.repsonse;

import entity.dto.UserDto;
import lombok.*;

import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListResponse {
    List<UserDto> users;
}
