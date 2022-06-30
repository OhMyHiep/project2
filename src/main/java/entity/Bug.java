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

//    @Override
//    public boolean equals(Object obj){
//        if(obj instanceof Bug){
//            Bug otherBug= (Bug)obj;
//            return this.bug_id==otherBug.getBug_id()
//                    && this.assignDate==null? otherBug.getAssignDate()==null:this.assignDate.equals(otherBug.getAssignDate())
//                    && this.issueDate==null? otherBug.issueDate
//        }
//    }
//
//    @Override
//    public int hashCode(){
//        return bug_id;
//    }
}