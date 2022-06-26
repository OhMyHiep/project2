package domain.repsonse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BugCommentResponse {
    private int commentId;
    private int bugId;
    private int commenterId;
    private String commentText;
    private Date commentDate;
}
