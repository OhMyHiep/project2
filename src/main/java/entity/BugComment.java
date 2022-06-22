package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BugComment {
    private Integer commentId;
    private Integer bugId;
    private Integer commenterUserId;
    private String commentText;
    private Date commentDate;
}
