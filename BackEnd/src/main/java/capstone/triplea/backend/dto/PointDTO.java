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
    public static List<PointDTO> calculateShortestRoute(List<PointDTO> points, PointDTO CenterPoints) {
        List<PointDTO> shortestRoute = new ArrayList<>();
        //현재 군집에 중심점이 포함되어 있으니 군집의 중심점이 출발점이기에 일단 제거
        for(int i =0; i<points.size();i++){
            if(CenterPoints.touristDestinationName.equals(points.get(i).touristDestinationName)){
                shortestRoute.add(points.get(i));
                points.remove(points.get(i));
            }
        }
        // 일단 먼저 출발점인 중심점 추가
        if(shortestRoute.isEmpty()){
            shortestRoute.add(CenterPoints);
        }

        //출발점(가까운지점)과 다음지점간의 짧은 거리를 선택해서 짧은순서대로 루트 형성
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
    
    //현재 루트의 총합 거리
    public static double calculateTotalDistance(List<PointDTO> points) {
        double totalDistance = 0;

        for (int i = 0; i < points.size() - 1; i++) {
            PointDTO currentPoint = points.get(i);
            PointDTO nextPoint = points.get(i + 1);
            totalDistance += distanceInKilometerByHaversine(currentPoint.getLatitude(),currentPoint.getLongitude(),nextPoint.getLatitude(),nextPoint.getLongitude());
        }

        return totalDistance;
    }

    //총 거리를 여행일수로 나눠서 나눈 거리의 여행지를 해당 일수로 포함시켜 루트 완성(여행 일수에 맞게 나눠지게됨)
    public static List<TravelPlannerDTO> distributePoints(List<PointDTO> points, int numDays, List<PointDTO> clusterPoints, List<PointDTO> centroids) {
        double totalDistance = calculateTotalDistance(points);
        
        //하루에 이동할 거리 = 총거리/일수
        double distancePerDay = totalDistance / numDays;
        int day = 1;
        
        //해당 일수에 너무 많이 여행지가 몰리지 않도록 하기 위함
        int totalcount = points.size()/numDays;
        int count = 0;

        List<TravelPlannerDTO> travelPlanners = new ArrayList<>();
        double currentDayDistance = 0;
        PointDTO nextPoint = null;
        //총거리를 여행일수로 나누고 나눈 Km에 따라 해당 일 수에 여행지 정보들을 추가
        for (int i = 0; i < points.size()-1; i++) {
            PointDTO currentPoint = points.get(i);
            nextPoint = points.get(i + 1);
            double distance = distanceInKilometerByHaversine(currentPoint.getLatitude(),currentPoint.getLongitude(),nextPoint.getLatitude(),nextPoint.getLongitude());
            currentDayDistance += distance;
            if (currentDayDistance <= distancePerDay && count <= totalcount) {
                travelPlanners.add(new TravelPlannerDTO().builder().day(String.valueOf(day)).order(i+1).
                        latitude(points.get(i).latitude).longitude(points.get(i).longitude).
                        touristDestinationName(points.get(i).touristDestinationName).build());
                count++;
            } else {
                if(travelPlanners.isEmpty()){
                    travelPlanners.add(new TravelPlannerDTO().builder().day(String.valueOf(day)).order(i+1).
                            latitude(points.get(i).latitude).longitude(points.get(i).longitude).
                            touristDestinationName(points.get(i).touristDestinationName).build());
                    count++;
                }
                else if(travelPlanners.get(i-1).getDay() == null) {
                    travelPlanners.add(new TravelPlannerDTO().builder().day(String.valueOf(day)).order(i+1).
                            latitude(points.get(i).latitude).longitude(points.get(i).longitude).
                            touristDestinationName(points.get(i).touristDestinationName).build());
                    count++;
                }
                else{
                    //설정한 일수보다 더 높게 되면 안됨
                    if(day < numDays){
                        day++;
                    }
                    travelPlanners.add(new TravelPlannerDTO().builder().day(String.valueOf(day)).order(i+1).
                            latitude(points.get(i).latitude).longitude(points.get(i).longitude).
                            touristDestinationName(points.get(i).touristDestinationName).build());
                    currentDayDistance = distance;
                    count=1;
                }
            }
        }
        //마지막 지점 추가
        if(nextPoint != null){
            travelPlanners.add(new TravelPlannerDTO().builder().day(String.valueOf(day)).order(points.size()).
                    latitude(nextPoint.getLatitude()).longitude(nextPoint.getLongitude()).
                    touristDestinationName(nextPoint.touristDestinationName).build());
        }
        
        //여기서 day가 모자르면 다른 가까운 군집을 선택해서 모자른 부분을 다른 군집의 루트를 참고하여 추가하고 남으면 버림
        if(day < numDays){
            for(int i =0; i<centroids.size(); i++){
                if(centroids.get(i).getCluster() == nextPoint.getCluster()){
                    centroids.remove(i);
                }
            }

            PointDTO closestPoint = nextPoint;
            double minDistance = Double.MAX_VALUE;

            for (PointDTO point : centroids) {
                double distance = PointDTO.distanceInKilometerByHaversine(closestPoint.getLatitude(),closestPoint.getLongitude(),point.getLatitude(),point.getLongitude());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPoint = point;
                }
            }

            //선택한 중심점의 군집 번호 찾기
            for (PointDTO pointDTO : clusterPoints) {
                if (closestPoint.getTouristDestinationName().equals(pointDTO.getTouristDestinationName())) {
                    closestPoint.setCluster(pointDTO.getCluster());
                }
            }
            List<PointDTO> clusterPoint = new ArrayList<>();
            //해당 군집 번호로 이뤄진 군집정보를 모든 군집정보가 들어있는 clusterPoints에서 clusterPoint로 뽑기
            for (PointDTO point : clusterPoints) {
                if (closestPoint.getCluster() == point.getCluster()) {
                    clusterPoint.add(point);
                }
            }

            PointDTO center = null;
            for(int i =0 ; i<centroids.size(); i++){
                if(closestPoint.getCluster() == centroids.get(i).getCluster()){
                    center = centroids.get(i);
                }
            }

            List<PointDTO> shortestRoot = PointDTO.calculateShortestRoute(clusterPoint, center);

            int total = travelPlanners.size();
            for(int i =0; i<shortestRoot.size(); i++){
                PointDTO currentPoint = shortestRoot.get(i);
                nextPoint = shortestRoot.get(i + 1);
                double distance = distanceInKilometerByHaversine(currentPoint.getLatitude(),currentPoint.getLongitude(),nextPoint.getLatitude(),nextPoint.getLongitude());
                currentDayDistance += distance;
                if(currentDayDistance <= distancePerDay && count<=totalcount){
                    travelPlanners.add(new TravelPlannerDTO().builder().day(String.valueOf(day)).order(total+i+1).
                            latitude(shortestRoot.get(i).latitude).longitude(shortestRoot.get(i).longitude).
                            touristDestinationName(shortestRoot.get(i).touristDestinationName).build());
                    count++;
                } else {
                    //설정한 일수보다 더 높게 되면 안됨
                    if (day <= numDays) {
                        day++;
                    }
                    else if (day >= numDays){
                        break;
                    }

                    travelPlanners.add(new TravelPlannerDTO().builder().day(String.valueOf(day)).order(total + i + 1).
                            latitude(shortestRoot.get(i).latitude).longitude(shortestRoot.get(i).longitude).
                            touristDestinationName(shortestRoot.get(i).touristDestinationName).build());
                    currentDayDistance = distance;
                    count = 1;
                }
                if(day == numDays){
                    return travelPlanners;
                }
            }
        }
        return travelPlanners;
    }
}
