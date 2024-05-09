package capstone.triplea.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelPlannerDTO {
    private int order; //여행 루틴 순서
    private String day; //날짜
    private String touristDestinationName; //관광지 명
    private Double latitude; //해당 관광지 위도
    private Double longitude; //해당 관광지 경도
    private String information;
}
