package capstone.triplea.backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelPlannerListDTO {
    private String code; //성공(S01), 오류(E01~E99)
    private String msg; // 상태코드 상세 메세지
    private List<TravelPlannerDTO> planners; //순서와 일수 대로 루틴이 짜여있는 여행리스트
}
