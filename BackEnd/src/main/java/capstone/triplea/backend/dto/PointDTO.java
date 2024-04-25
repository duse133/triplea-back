package capstone.triplea.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointDTO {
    private double latitude;
    private double longitude;
    private String touristDestinationName;
    private int cluster;

    public PointDTO(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.cluster = -1; // Initially not assigned to any cluster
    }

    public PointDTO(double latitude, double longitude, String touristDestinationName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.touristDestinationName = touristDestinationName;
        this.cluster = -1; // Initially not assigned to any cluster
    }

    // Method to calculate distance between two points using Haversine formula
    public static double distanceInKilometerByHaversine(double x1, double y1, double x2, double y2) {
        double distance;
        double radius = 6371; // Earth's radius in kilometers
        double toRadian = Math.PI / 180;

        double deltaLatitude = Math.abs(x1 - x2) * toRadian;
        double deltaLongitude = Math.abs(y1 - y2) * toRadian;

        double sinDeltaLat = Math.sin(deltaLatitude / 2);
        double sinDeltaLng = Math.sin(deltaLongitude / 2);
        double squareRoot = Math.sqrt(
                sinDeltaLat * sinDeltaLat +
                        Math.cos(x1 * toRadian) * Math.cos(x2 * toRadian) * sinDeltaLng * sinDeltaLng);

        distance = 2 * radius * Math.asin(squareRoot);

        return distance;
    }

}
