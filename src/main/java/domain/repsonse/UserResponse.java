package domain.repsonse;


import entity.dto.BugCommentDto;
import entity.dto.BugDto;
import entity.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Integer user_id;
    private String firstname;
    private String lastname;
    private List<BugDto> createdBugs;
    private List<BugDto> assignedBugs;
    private List<BugCommentDto> comments;
    private List<RoleDto> roles;


}
