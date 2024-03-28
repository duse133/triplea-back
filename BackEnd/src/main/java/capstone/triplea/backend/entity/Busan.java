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
public class Busan {

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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String introduction;

    @Column(nullable = false)
    private String agencyPhoneNumber;

    @Column(nullable = false)
    private String providerOrganization;

    @Column(nullable = false)
    private String managementOrganization;
}
