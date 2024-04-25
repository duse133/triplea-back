package capstone.triplea.backend.service;

import capstone.triplea.backend.dto.PointDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KMeansService {

    private int k; // Number of clusters (군집 갯수)
    private int maxIterations; // Maximum number of iterations (최대 반복)
    private List<PointDTO> centroids; // Centroids of clusters (각 군집마다 중심점)

    public List<PointDTO> clusterPoints(List<PointDTO> centroids, List<PointDTO> pointDTOS, int k, int maxIterations) {
        this.k = k;
        this.maxIterations = maxIterations;
        this.centroids = new ArrayList<>();
        return cluster(centroids, pointDTOS);
    }

    // Method to initialize centroids randomly (각 군집의 중심점들 = 각 지역의 우선순위가 높은 여행지들이 됨)
    private void initializeCentroids(List<PointDTO> pointDTOS) {
        for (int i = 0; i < k; i++) {
            centroids.add(new PointDTO(pointDTOS.get(i).getLatitude(), pointDTOS.get(i).getLongitude()));
        }
    }

    // Method to assign each point to the nearest centroid (각 점들을 가장 가까운 중심점들에게 할당하는 메소드, 하버사인 공식을 이용하여 거리를 구함)
    private void assignCluster(List<PointDTO> pointDTOS) {
        for (PointDTO pointDTO : pointDTOS) {
            double minDistance = Double.MAX_VALUE;
            int clusterIndex = -1;
            for (int i = 0; i < centroids.size(); i++) {
                double distance = PointDTO.distanceInKilometerByHaversine(pointDTO.getLatitude(), pointDTO.getLongitude(), centroids.get(i).getLatitude(), centroids.get(i).getLongitude());
                if (distance < minDistance) {
                    minDistance = distance;
                    clusterIndex = i;
                }
            }
            pointDTO.setCluster(clusterIndex);
        }
    }

    // Method to update centroids based on mean of points in each cluster (각 클러스터의 포인트 평균을 기반으로 중심을 업데이트, 각 군집의 점들을 평균을 내어 중심점을 정하는 메소드)
    private void updateCentroids(List<PointDTO> pointDTOS) {
        for (int i = 0; i < centroids.size(); i++) {
            double sumLat = 0, sumLng = 0;
            int clusterSize = 0;
            for (PointDTO pointDTO : pointDTOS) {
                if (pointDTO.getCluster() == i) {
                    sumLat += pointDTO.getLatitude();
                    sumLng += pointDTO.getLongitude();
                    clusterSize++;
                }
            }
            if (clusterSize > 0) {
                double newLat = sumLat / clusterSize;
                double newLng = sumLng / clusterSize;
                centroids.get(i).setLatitude(newLat);
                centroids.get(i).setLongitude(newLng);
            }
        }
    }

    // Method to perform K-Means clustering (K-Means 클러스터링 알고리즘 : 중심점 초기화(처음 중심점을 뭐로 할당한건지가 가장 중요) -> 반복학습 100번으로 설정되어 있음(중심점을 기점으로 군집화, 각각의 군집의 점들을 평균을 내어 중심점을 만듬))
    private List<PointDTO> cluster(List<PointDTO> centroids, List<PointDTO> pointDTOS) {
        initializeCentroids(centroids);
        for (int iter = 0; iter < maxIterations; iter++) {
            assignCluster(pointDTOS);
            updateCentroids(pointDTOS);
        }
        return pointDTOS;
    }
}

