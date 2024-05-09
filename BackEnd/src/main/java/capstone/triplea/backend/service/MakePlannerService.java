package capstone.triplea.backend.service;

import capstone.triplea.backend.dto.PointDTO;
import capstone.triplea.backend.dto.TravelPlannerDTO;
import capstone.triplea.backend.dto.UserInputDTO;
import capstone.triplea.backend.entity.*;
import capstone.triplea.backend.exception.CParameterNotFound;
import capstone.triplea.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MakePlannerService {

    private final SeoulRepository seoulRepository;
    private final BusanRepository busanRepository;
    private final ChungnamRepository chungnamRepository;
    private final ChungbukRepository chungbukRepository;
    private final DaeguRepository daeguRepository;
    private final DeajeonRepository deajeonRepository;
    private final GangwondoRepository gangwondoRepository;
    private final GwangjuRepository gwangjuRepository;
    private final GyenoggidoRepository gyenoggidoRepository;
    private final GyeongnamRepository gyeongnamRepository;
    private final GyongbukRepository gyongbukRepository;
    private final IncheonRepository incheonRepository;
    private final JejuRepository jejuRepository;
    private final JeonbukRepository jeonbukRepository;
    private final JeonnamRepository jeonnamRepository;
    private final SejongRepository sejongRepository;
    private final UlsanRepository ulsanRepository;

    private final KMeansService kMeansService; //해당 지역 우선순위 여행지를 기점으로 군집화
    
    //지역의 중심점을 가지고 군집 형성 -> 군집의 최단거리로 루트(순서)생성 -> 해당 루트의 총 길이를 일수로 쪼개어 길이에 맞게 해당 일수에 적용
    //일단 루트 1개를 만들어서 보냄
    public List<TravelPlannerDTO> makePlanners(UserInputDTO userInputData){

        List<PointDTO> centroids = new ArrayList<>(); //각 지역의 중심점
        List<PointDTO> points = new ArrayList<>(); //각 지역의 모든 여행지 정보들
        List<PointDTO> clusterPoints = new ArrayList<>(); //각 지역의 여행지 정보들을 군집화
        List<PointDTO> clusterPoint = new ArrayList<>(); //군집화된 것 중 한 곳의 여행지 정보들
        List<PointDTO> shortPoints = new ArrayList<>(); //군집의 중심점을 시작으로 해당 군집내의 지역들의 최단거리를 통해 루트 생성
        
        //유저가 입력한 지역정보에서 중심점과 각지역의 여행지 정보들을 가져옴
        switch (userInputData.getArea()) {
            case "서울" -> {
                //서울 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
                List<Seoul> seoulCenter = this.seoulRepository.findByPriority(1);
                for (Seoul seoul : seoulCenter) {
                    centroids.add(new PointDTO(seoul.getLatitude(), seoul.getLongitude(), seoul.getAttractionName(), seoul.getIntroduction()));
                }

                //해당 지역의 데이터들을 가져옴
                List<Seoul> seoulList = this.seoulRepository.findAll();
                for (Seoul seoul : seoulList) {
                    points.add(new PointDTO(seoul.getLatitude(), seoul.getLongitude(), seoul.getAttractionName(), seoul.getIntroduction()));
                }
            }
            case "부산" -> {
                //부산 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
                List<Busan> busanCenter = this.busanRepository.findByPriority(1);
                for (Busan busan : busanCenter) {
                    centroids.add(new PointDTO(busan.getLatitude(), busan.getLongitude(), busan.getAttractionName(), busan.getIntroduction()));
                }

                //해당 지역의 데이터들을 가져옴
                List<Busan> busanList = this.busanRepository.findAll();
                for (Busan busan : busanList) {
                    points.add(new PointDTO(busan.getLatitude(), busan.getLongitude(), busan.getAttractionName(), busan.getIntroduction()));
                }
            }
            case "대구" -> {
                //대구 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
                List<Daegu> daeguCenter = this.daeguRepository.findByPriority(1);
                for (Daegu daegu : daeguCenter) {
                    centroids.add(new PointDTO(daegu.getLatitude(), daegu.getLongitude(), daegu.getAttractionName(), daegu.getIntroduction()));
                }

                //해당 지역의 데이터들을 가져옴
                List<Daegu> daeguList = this.daeguRepository.findAll();
                for (Daegu daegu : daeguList) {
                    points.add(new PointDTO(daegu.getLatitude(), daegu.getLongitude(), daegu.getAttractionName(), daegu.getIntroduction()));
                }
            }
            case "인천" -> {
                //인천 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
                List<Incheon> incheonCenter = this.incheonRepository.findByPriority(1);
                for (Incheon incheon : incheonCenter) {
                    centroids.add(new PointDTO(incheon.getLatitude(), incheon.getLongitude(), incheon.getAttractionName(), incheon.getIntroduction()));
                }

                //해당 지역의 데이터들을 가져옴
                List<Incheon> incheonList = this.incheonRepository.findAll();
                for (Incheon incheon : incheonList) {
                    points.add(new PointDTO(incheon.getLatitude(), incheon.getLongitude(), incheon.getAttractionName(), incheon.getIntroduction()));
                }
            }
            case "광주" -> {
                //광주 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
                List<Gwangju> gwangjuCenter = this.gwangjuRepository.findByPriority(1);
                for (Gwangju gwangju : gwangjuCenter) {
                    centroids.add(new PointDTO(gwangju.getLatitude(), gwangju.getLongitude(), gwangju.getAttractionName(), gwangju.getIntroduction()));
                }

                //해당 지역의 데이터들을 가져옴
                List<Gwangju> gwangjuList = this.gwangjuRepository.findAll();
                for (Gwangju gwangju : gwangjuList) {
                    points.add(new PointDTO(gwangju.getLatitude(), gwangju.getLongitude(), gwangju.getAttractionName(), gwangju.getIntroduction()));
                }
            }
            case "대전" -> {
                //대전 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
                List<Deajeon> deajeonCenter = this.deajeonRepository.findByPriority(1);
                for (Deajeon deajeon : deajeonCenter) {
                    centroids.add(new PointDTO(deajeon.getLatitude(), deajeon.getLongitude(), deajeon.getAttractionName(), deajeon.getIntroduction()));
                }

                //해당 지역의 데이터들을 가져옴
                List<Deajeon> deajeonList = this.deajeonRepository.findAll();
                for (Deajeon deajeon : deajeonList) {
                    points.add(new PointDTO(deajeon.getLatitude(), deajeon.getLongitude(), deajeon.getAttractionName(), deajeon.getIntroduction()));
                }
            }
            case "울산" -> {
                //울산 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
                List<Ulsan> ulsanCenter = this.ulsanRepository.findByPriority(1);
                for (Ulsan ulsan : ulsanCenter) {
                    centroids.add(new PointDTO(ulsan.getLatitude(), ulsan.getLongitude(), ulsan.getAttractionName(), ulsan.getIntroduction()));
                }

                //해당 지역의 데이터들을 가져옴
                List<Ulsan> ulsanList = this.ulsanRepository.findAll();
                for (Ulsan ulsan : ulsanList) {
                    points.add(new PointDTO(ulsan.getLatitude(), ulsan.getLongitude(), ulsan.getAttractionName(), ulsan.getIntroduction()));
                }
            }
            case "세종" -> {
                //세종 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
                List<Sejong> sejongCenter = this.sejongRepository.findByPriority(1);
                for (Sejong sejong : sejongCenter) {
                    centroids.add(new PointDTO(sejong.getLatitude(), sejong.getLongitude(), sejong.getAttractionName(), sejong.getIntroduction()));
                }

                //해당 지역의 데이터들을 가져옴
                List<Sejong> sejongList = this.sejongRepository.findAll();
                for (Sejong sejong : sejongList) {
                    points.add(new PointDTO(sejong.getLatitude(), sejong.getLongitude(), sejong.getAttractionName(), sejong.getIntroduction()));
                }
            }
            case "경기" -> {
                //경기 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
                List<Gyeonggido> gyeonggidoCenter = this.gyenoggidoRepository.findByPriority(1);
                for (Gyeonggido gyeonggido : gyeonggidoCenter) {
                    centroids.add(new PointDTO(gyeonggido.getLatitude(), gyeonggido.getLongitude(), gyeonggido.getAttractionName(), gyeonggido.getIntroduction()));
                }

                //해당 지역의 데이터들을 가져옴
                List<Gyeonggido> gyeonggidoList = this.gyenoggidoRepository.findAll();
                for (Gyeonggido gyeonggido : gyeonggidoList) {
                    points.add(new PointDTO(gyeonggido.getLatitude(), gyeonggido.getLongitude(), gyeonggido.getAttractionName(), gyeonggido.getIntroduction()));
                }
            }
            case "강원" -> {
                //강원 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
                List<Gangwondo> gangwondoCenter = this.gangwondoRepository.findByPriority(1);
                for (Gangwondo gangwondo : gangwondoCenter) {
                    centroids.add(new PointDTO(gangwondo.getLatitude(), gangwondo.getLongitude(), gangwondo.getAttractionName(), gangwondo.getIntroduction()));
                }

                //해당 지역의 데이터들을 가져옴
                List<Gangwondo> gangwondoList = this.gangwondoRepository.findAll();
                for (Gangwondo gangwondo : gangwondoList) {
                    points.add(new PointDTO(gangwondo.getLatitude(), gangwondo.getLongitude(), gangwondo.getAttractionName(), gangwondo.getIntroduction()));
                }
            }
            case "충북" -> {
                //충북 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
                List<Chungbuk> chungbukCenter = this.chungbukRepository.findByPriority(1);
                for (Chungbuk chungbuk : chungbukCenter) {
                    centroids.add(new PointDTO(chungbuk.getLatitude(), chungbuk.getLongitude(), chungbuk.getAttractionName(), chungbuk.getIntroduction()));
                }

                //해당 지역의 데이터들을 가져옴
                List<Chungbuk> chungbukList = this.chungbukRepository.findAll();
                for (Chungbuk chungbuk : chungbukList) {
                    points.add(new PointDTO(chungbuk.getLatitude(), chungbuk.getLongitude(), chungbuk.getAttractionName(), chungbuk.getIntroduction()));
                }
            }
            case "충남" -> {
                //충남 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
                List<Chungnam> chungnamCenter = this.chungnamRepository.findByPriority(1);
                for (Chungnam chungnam : chungnamCenter) {
                    centroids.add(new PointDTO(chungnam.getLatitude(), chungnam.getLongitude(), chungnam.getAttractionName(), chungnam.getIntroduction()));
                }

                //해당 지역의 데이터들을 가져옴
                List<Chungnam> chungnamList = this.chungnamRepository.findAll();
                for (Chungnam chungnam : chungnamList) {
                    points.add(new PointDTO(chungnam.getLatitude(), chungnam.getLongitude(), chungnam.getAttractionName(), chungnam.getIntroduction()));
                }
            }
            case "전북" -> {
                //전북 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
                List<Jeonbuk> jeonbukCenter = this.jeonbukRepository.findByPriority(1);
                for (Jeonbuk jeonbuk : jeonbukCenter) {
                    centroids.add(new PointDTO(jeonbuk.getLatitude(), jeonbuk.getLongitude(), jeonbuk.getAttractionName(), jeonbuk.getIntroduction()));
                }

                //해당 지역의 데이터들을 가져옴
                List<Jeonbuk> jeonbukList = this.jeonbukRepository.findAll();
                for (Jeonbuk jeonbuk : jeonbukList) {
                    points.add(new PointDTO(jeonbuk.getLatitude(), jeonbuk.getLongitude(), jeonbuk.getAttractionName(), jeonbuk.getIntroduction()));
                }
            }
            case "전남" -> {
                //전남 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
                List<Jeonnam> jeonnamCenter = this.jeonnamRepository.findByPriority(1);
                for (Jeonnam jeonnam : jeonnamCenter) {
                    centroids.add(new PointDTO(jeonnam.getLatitude(), jeonnam.getLongitude(), jeonnam.getAttractionName(), jeonnam.getIntroduction()));
                }

                //해당 지역의 데이터들을 가져옴
                List<Jeonnam> jeonnamList = this.jeonnamRepository.findAll();
                for (Jeonnam jeonnam : jeonnamList) {
                    points.add(new PointDTO(jeonnam.getLatitude(), jeonnam.getLongitude(), jeonnam.getAttractionName(), jeonnam.getIntroduction()));
                }
            }
            case "경북" -> {
                //경북 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
                List<Gyongbuk> gyongbukCenter = this.gyongbukRepository.findByPriority(1);
                for (Gyongbuk gyongbuk : gyongbukCenter) {
                    centroids.add(new PointDTO(gyongbuk.getLatitude(), gyongbuk.getLongitude(), gyongbuk.getAttractionName(), gyongbuk.getIntroduction()));
                }

                //해당 지역의 데이터들을 가져옴
                List<Gyongbuk> gyongbukList = this.gyongbukRepository.findAll();
                for (Gyongbuk gyongbuk : gyongbukList) {
                    points.add(new PointDTO(gyongbuk.getLatitude(), gyongbuk.getLongitude(), gyongbuk.getAttractionName(), gyongbuk.getIntroduction()));
                }
            }
            case "경남" -> {
                //경남 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
                List<Gyeongnam> gyeongnamCenter = this.gyeongnamRepository.findByPriority(1);
                for (Gyeongnam gyeongnam : gyeongnamCenter) {
                    centroids.add(new PointDTO(gyeongnam.getLatitude(), gyeongnam.getLongitude(), gyeongnam.getAttractionName(), gyeongnam.getIntroduction()));
                }

                //해당 지역의 데이터들을 가져옴
                List<Gyeongnam> gyeongnamList = this.gyeongnamRepository.findAll();
                for (Gyeongnam gyeongnam : gyeongnamList) {
                    points.add(new PointDTO(gyeongnam.getLatitude(), gyeongnam.getLongitude(), gyeongnam.getAttractionName(), gyeongnam.getIntroduction()));
                }
            }
            case "제주" -> {
                //제주 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
                List<Jeju> jejuCenter = this.jejuRepository.findByPriority(1);
                for (Jeju jeju : jejuCenter) {
                    centroids.add(new PointDTO(jeju.getLatitude(), jeju.getLongitude(), jeju.getAttractionName(), jeju.getIntroduction()));
                }

                //해당 지역의 데이터들을 가져옴
                List<Jeju> jejuList = this.jejuRepository.findAll();
                for (Jeju jeju : jejuList) {
                    points.add(new PointDTO(jeju.getLatitude(), jeju.getLongitude(), jeju.getAttractionName(), jeju.getIntroduction()));
                }
            }
        }

        //군집 중심점, 해당 지역의 여행지들, 군집수, 반복횟수들의 정보를 가지고 군집 형성(cluster : 중심점을 기준으로 구분짓는 군집)
        clusterPoints = kMeansService.clusterPoints(centroids, points, centroids.size(), 100);

        //사용자가 입력한 숙소정보를 가지고 해당 숙소에서 가장 가까운 군집의 중심점을 선택하여 해당 군집에서 중심점을 가지고 최단거리 계산을 통해 루트 생성
        //사용자가 아직 숙소정보를 입력하지 않았다면 군집화된것 중 랜덤으로 선택해서 해당 군집의 최단거리를 계한하여 루트 생성
        //또한 해당 군집의 여행지 갯수가 사용자가 입력한 일수 보다 작으면 추가적으로 근처에 군집을 이어 군집 하나로 합침.

        PointDTO closestPoint = null; //숙소(출발점)와 가장 가까운 중심점
        double minDistance = Double.MAX_VALUE;
        
        //사용자가 숙소정보를 입력했거나 입력하지 않았을때
        if(userInputData.getAccommodationName().isEmpty()){
            //사용자가 숙소정보를 입력하지 않았을때 랜덤한 중심점 선택
            Random random = new Random();
            int randomNumber = random.nextInt(centroids.size());
            closestPoint = centroids.get(randomNumber);
        }
        else{
            // 숙소 이름만 있고 위도,경도 정보가 없는 경우 필수 파라미터 누락 메세지 전달
            if(userInputData.getAccommodate_longitude() == 0 && userInputData.getAccommodate_latitude() == 0){
                throw new CParameterNotFound();
            }
            //사용자가 입력한 해당 숙소와 가장 가까운 중심점 찾기
            for (PointDTO point : centroids) {
                double distance = PointDTO.distanceInKilometerByHaversine(userInputData.getAccommodate_latitude(),userInputData.getAccommodate_longitude(),point.getLatitude(),point.getLongitude());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPoint = point;
                }
            }
        }

        //선택한 중심점의 군집 번호를 만들어진 군집 내에서 찾기
        for (PointDTO pointDTO : clusterPoints) {
            if (closestPoint.getTouristDestinationName().equals(pointDTO.getTouristDestinationName())) {
                closestPoint.setCluster(pointDTO.getCluster());
            }
        }

        //해당 군집 번호로 이뤄진 군집정보를 모든 군집정보가 들어있는 clusterPoints에서 clusterPoint로 뽑기
        for (PointDTO point : clusterPoints) {
            if (closestPoint.getCluster() == point.getCluster()) {
                clusterPoint.add(point);
            }
        }

        //해당 군집의 여행지 갯수가 사용자가 입력한 일 수 보다 적을 때 다른 가까운 군집을 원래 군집과 합침
        if(clusterPoint.size() < Integer.parseInt(userInputData.getDay())){
            //선택한 군집을 중심점만 있는 centroids에서 삭제
            for(int i =0 ;i<centroids.size();i++){
                if(centroids.get(i).getCluster() == closestPoint.getCluster()){
                    centroids.remove(i);
                }
            }
            //남은 군집에서 선택한 군집과 가장 가까운 군집 선택
            minDistance = Double.MAX_VALUE;
            for (PointDTO centroid : centroids) {
                double distance = PointDTO.distanceInKilometerByHaversine(userInputData.getAccommodate_latitude(),userInputData.getAccommodate_longitude(), centroid.getLatitude(), centroid.getLongitude());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPoint = centroid;
                }
            }
            //closetPoint에 cluster 값이 초기화 되어 있음 따라서 추가해줘야함
            for (PointDTO point : clusterPoints) {
                if (closestPoint.getTouristDestinationName().equals(point.getTouristDestinationName())) {
                    closestPoint.setCluster(point.getCluster());
                }
            }
            //선택한 군집 번호로 이뤄진 군집정보를 모든 군집정보가 들어있는 clusterPoints에서 clusterPoint로 뽑기
            for (PointDTO point : clusterPoints) {
                if (closestPoint.getCluster() == point.getCluster()) {
                    clusterPoint.add(point);
                }
            }
        }

        //해당군집의 중심점을 기준으로 최단거리 알고리즘으로 가장 짧은 거리의 루트 생성
        System.out.println(clusterPoint.size());
        List<PointDTO> shortPoint = new ArrayList<>();
        shortPoints = PointDTO.calculateShortestRoute(clusterPoint,closestPoint);

        //사용자 입력 강도에 따라 여행지루트 갯수 조절(보통는 갯수 일당 4개까지, 강할때는 일당 7개 이상)
        int dayPerCount = 0;

        if(userInputData.getStrength().equals("보통")){
            dayPerCount = Integer.parseInt(userInputData.getDay()) * 4;
            //현재 루트의 여행지 갯수가 강도를 약함으로 설정한 갯수보다 큰 경우 해당 크기로 줄여야함
            if(shortPoints.size() > dayPerCount){
                for(int i =0; i<= dayPerCount; i++){
                    shortPoint.add(shortPoints.get(i));
                }
                return PointDTO.distributePoints(shortPoint, Integer.parseInt(userInputData.getDay()), clusterPoints, centroids);
            }
        }else if (userInputData.getStrength().equals("강함")){
            dayPerCount = Integer.parseInt(userInputData.getDay()) * 7;
            while(shortPoints.size() <= dayPerCount) {
                for(int i =0 ;i<centroids.size();i++){
                    if(centroids.get(i).getCluster() == closestPoint.getCluster()){
                        centroids.remove(i);
                    }
                }
                if(centroids.isEmpty()){
                    break;
                }
                closestPoint = centroids.get(0);
                //closetPoint에 cluster 값이 초기화 되어 있음 따라서 추가해줘야함
                for (PointDTO point : clusterPoints) {
                    if (closestPoint.getTouristDestinationName().equals(point.getTouristDestinationName())) {
                        closestPoint.setCluster(point.getCluster());
                    }
                }
                //선택한 군집 번호로 이뤄진 군집정보를 모든 군집정보가 들어있는 clusterPoints에서 clusterPoint로 뽑기
                for (PointDTO point : clusterPoints) {
                    if (closestPoint.getCluster() == point.getCluster()) {
                        shortPoints.add(point);
                    }
                }
            }
            shortPoint = PointDTO.calculateShortestRoute(shortPoints,closestPoint);
            return PointDTO.distributePoints(shortPoint, Integer.parseInt(userInputData.getDay()), clusterPoints, centroids);
        }else if (userInputData.getStrength().isEmpty()){
            throw new CParameterNotFound();
        }

        //추가적으로 루트를 생성했으면 해당 루트를 여행일수에 맞게 각 일수에 여행루트들을 조절해야함
        //일단 전체 거리를 계산하여 사용자가 입력한 일 수에 맞춰 거리를 짜르고 해당 거리에 있는 여행지를 해당 일에 포함시켜서 나눔(거리에 맞게 불규칙적으로 나눠지게 됨)
        //최종적으로 유저가 입력한 일수로 쪼개고 순서까지 정해진 루트 1개 만들어서 리턴
        return PointDTO.distributePoints(shortPoints, Integer.parseInt(userInputData.getDay()), clusterPoints, centroids);
    }
}
