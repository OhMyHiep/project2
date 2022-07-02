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
    private String title;

    @Override
    public boolean equals(Object obj){
        if(obj!=null && obj instanceof Bug){
            Bug otherBug= (Bug)obj;
//            System.out.println(this);
//            System.out.println(otherBug);
            return this.bug_id==null? otherBug.getBug_id()==null: this.bug_id.equals(otherBug.getBug_id())
                    && (this.getAssignDate()==null ? otherBug.getAssignDate()==null:this.assignDate.toLocalDate().equals(otherBug.getAssignDate()==null?null:otherBug.getAssignDate().toLocalDate()))
                    && (this.issueDate==null ? otherBug.getIssueDate()==null:this.issueDate.toLocalDate().equals(otherBug.getIssueDate()==null?null:otherBug.getIssueDate().toLocalDate()))
                    && (this.closeDate==null ? otherBug.getCloseDate()==null:this.closeDate.toLocalDate().equals(otherBug.getCloseDate()==null?null:otherBug.getCloseDate().toLocalDate()))
                    && (this.creator_id==null? otherBug.getCreator_id()==null: this.creator_id.equals(otherBug.getCreator_id()))
                    && (this.description==null?otherBug.description==null:this.description.equals(otherBug.getDescription()))
                    && (this.status==null? otherBug.getStatus()==null : this.status.equals(otherBug.getStatus()))
                    && (this.urgency==otherBug.urgency)
                    && (this.assigned_to==otherBug.assigned_to)
                    && (this.severity==otherBug.getSeverity())
                    && (this.title==null?otherBug.getTitle()==null:this.title.equals(otherBug.getTitle()))
                    ;
        }

        else return false;
    }

    @Override
    public int hashCode(){
        return bug_id;
    }
}