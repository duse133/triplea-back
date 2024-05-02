package capstone.triplea.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Jeju {

    @Id
    private String attractionName;

    @Column(nullable = false)
    private String districtClassification;

    private String mapNameAddress;

    private String numberAddress;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double area;

    private String publicConvenienceInfo;

    private String accommodationInfo;

    private String exerciseEntertainmentInfo;

    private String recreationalCulturalInfo;

    private String hospitalityInfo;

    private String supportInfo;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    private String agencyPhoneNumber;

    private String providerOrganization;

    private String managementOrganization;

    @Column(nullable = false)
    private int weight;
}
