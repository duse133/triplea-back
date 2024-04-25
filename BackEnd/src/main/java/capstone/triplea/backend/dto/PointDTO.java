package capstone.triplea.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    
    //중심점을 출발점으로 해당 군집에 있는 모든 지역들과의 거리를 계산하여 짧은 순으로 루트를 만들어줌
    public static List<PointDTO> calculateShortestRoute(List<PointDTO> points) {
        List<PointDTO> shortestRoute = new ArrayList<>();
        shortestRoute.add(points.remove(0)); // Start with the first point <- 이게 출발지점인데 일단 중심점을 넣을 것임

        while (!points.isEmpty()) {
            PointDTO nearestPoint = null;
            double shortestDistance = Double.MAX_VALUE;

            PointDTO lastPoint = shortestRoute.get(shortestRoute.size() - 1);

            for (PointDTO point : points) {
                double distance = PointDTO.distanceInKilometerByHaversine(lastPoint.getLatitude(),lastPoint.getLongitude(), point.getLatitude(), point.getLongitude());
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    nearestPoint = point;
                }
            }

            shortestRoute.add(nearestPoint);
            points.remove(nearestPoint);
        }
        return shortestRoute;
    }

}
