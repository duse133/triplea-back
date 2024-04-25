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
    AccommodationDTO accommodationInfo;
}
