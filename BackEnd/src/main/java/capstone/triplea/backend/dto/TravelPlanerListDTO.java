package capstone.triplea.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelPlanerListDTO {
    private int number; //여행 플래너 추천 루틴 갯수 구분 숫자(1~3)
    private List<TravelPlanerDTO> planers; //순서와 일수 대로 루틴이 짜여있는 여행리스트
}
