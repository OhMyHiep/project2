package entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugDto {
    private String url;
    private String title;
    private Date issueDate;
    private Integer status;
    private Date closeDate;
    private Date assignDate;
    private Integer urgency;
    private Integer severity;
}
