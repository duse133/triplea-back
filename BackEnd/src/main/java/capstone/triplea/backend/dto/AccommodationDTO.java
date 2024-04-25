package capstone.triplea.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccommodationDTO {
    private double latitude;
    private double longitude; 
    private String accommodationName; //숙소 이름
}
