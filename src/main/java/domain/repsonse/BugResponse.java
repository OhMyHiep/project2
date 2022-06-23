package domain.repsonse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugResponse {
    private Integer bug_id;
    private Date assignDate;
    private Date issueDate;
    private Date closeDate;
    private Integer creator_id;
    private String description;
    private Integer status;
    private Integer urgency;
    private Integer assignedTo;
    private Integer severity;
}
