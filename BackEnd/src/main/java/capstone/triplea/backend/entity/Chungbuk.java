package capstone.triplea.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chungbuk {

    @Id
    private String attractionName;

    @Column(nullable = false)
    private String map_name_address;

    @Column(nullable = false)
    private Double latitude;

    private Double longitude;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Column(nullable = false)
    private int priority;
}
