package entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Bug {
    private Integer bug_id;
    private Date assignDate;
    private Date issueDate;
    private Date closeDate;
    private Integer creator_id;
    private String description;
    private Integer status;
    private Integer urgency;
    private Integer assigned_to;
    private Integer severity;

    @Override
    public boolean equals(Object obj){
        if(obj!=null && obj instanceof Bug){
            Bug otherBug= (Bug)obj;
            return this.bug_id==otherBug.getBug_id()
                    && (this.getAssignDate()==null ? otherBug.getAssignDate()==null:this.getAssignDate().equals(otherBug.getAssignDate()))
                    && (this.issueDate==null ? otherBug.getIssueDate()==null:this.issueDate.equals(otherBug.getIssueDate()))
                    && (this.closeDate==null ? otherBug.getCloseDate()==null:this.closeDate.equals(otherBug.getCloseDate()))
                    && (this.creator_id==otherBug.getCreator_id())
                    && (this.description==null?otherBug.description==null:this.description.equals(otherBug.getDescription()))
                    && this.status==otherBug.status
                    && this.urgency==otherBug.urgency
                    && this.assigned_to==otherBug.assigned_to
                    && this.severity==otherBug.getSeverity();
        }
        else return false;
    }

    @Override
    public int hashCode(){
        return bug_id;
    }
}