package domain.repsonse;

import entity.dto.BugDto;
import entity.dto.BugCommentDto;
import entity.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugResponse {
    private Integer bug_id;
    private String title;
    private Date assignDate;
    private Date issueDate;
    private Date closeDate;
    private String description;
    private Integer status;
    private Integer urgency;
    private Integer severity;
    private UserDto creator;
    private UserDto assigned_to;
    private List<BugCommentDto> comment;
}
