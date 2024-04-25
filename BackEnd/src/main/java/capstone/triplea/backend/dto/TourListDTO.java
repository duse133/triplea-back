package capstone.triplea.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TourListDTO {
    private String code; //성공(S01), 오류(E01~E99)
    private String msg; // 상태코드 상세 메세지
    private String touristDestinationName; //관광지 명
    private Double latitude; //해당 관광지 위도
    private Double longitude; //해당 관광지 경도
    private String introduction; // 해당 관광지 설명
}
