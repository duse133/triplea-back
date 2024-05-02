package capstone.triplea.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInputDTO {
    private String area;
    private String day;
    private String strength;

    private String accommodationName; //숙소 이름
    private double accommodate_latitude; //숙소 위도
    private double accommodate_longitude; //숙소 경도
}
