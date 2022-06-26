package domain.repsonse;

import entity.dto.BugDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugListResponse {
    List<BugDto> bugs;
}
