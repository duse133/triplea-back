package capstone.triplea.backend.service;

import capstone.triplea.backend.dto.PointDTO;
import capstone.triplea.backend.dto.TravelPlannerDTO;
import capstone.triplea.backend.dto.TravelPlannerListDTO;
import capstone.triplea.backend.dto.UserInputDTO;
import capstone.triplea.backend.entity.*;
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
    int randomUniqueNumberCount = 0; //고유한 랜덤 번호
    ArrayList<Integer> uniqueNumbers = new ArrayList<>(); //고유한 숫자

    private final KMeansService kMeansService; //해당 지역 우선순위 여행지를 기점으로 군집화
    
    //군집 형성 메소드(추후 군집내에 최단거리 알고리즘 만들어서 루트도 만들 예정)
    public List<PointDTO> makeClustering(){

        List<PointDTO> centroids = new ArrayList<>(); //각 지역의 중심점
        List<PointDTO> points = new ArrayList<>(); //각 지역의 여행지 정보들
        List<PointDTO> clusterPoints = new ArrayList<>(); //각 지역의 여행지 정보들
        List<PointDTO> shortPoints = new ArrayList<>(); //군집의 중심점을 시작으로 해당 군집내의 지역들의 최단거리를 통해 루트 생성
        
        //율산 데이터베이스의 weight 값이 1인 즉 중요도가 높은 지역 가져와서 중심점으로 설정
        List<Ulsan> ulsanCenter = this.ulsanRepository.findByWeight(1);
        for (Ulsan ulsan : ulsanCenter) {
            centroids.add(new PointDTO(ulsan.getLatitude(), ulsan.getLongitude()));
        }
        
        //해당 지역의 데이터들을 가져옴
        List<Ulsan> ulsanList = this.ulsanRepository.findAll();
        for (Ulsan ulsan : ulsanList) {
            points.add(new PointDTO(ulsan.getLatitude(), ulsan.getLongitude(), ulsan.getAttractionName()));
        }

        //군집 중심점, 해당 지역의 여행지들, 군집수, 반복횟수들의 정보를 가지고 군집 형성(cluster : 중심점을 기준으로 구분짓는 군집)
        clusterPoints = kMeansService.clusterPoints(centroids, points, centroids.size(), 100);

        //사용자가 입력한 숙소정보를 가지고 해당 숙소에서 가장 가까운 군집의 중심점을 선택하여 해당 군집에서 중심점을 가지고 최단거리 계산을 통해 루트 생성

        return clusterPoints;
    }

    //사용자가 입력한 3개의 데이터(지역, 여행일수, 여행강도)에 따라 3개의 랜덤한 관광지를 연결지어 여행 루틴을 만들어주는 로직
    public List<TravelPlannerListDTO> makePlannerTourist(UserInputDTO UserInputData){
        List<TravelPlannerListDTO> TravelPlannerList = new ArrayList<>();
        randomUniqueNumberCount = 0;
        uniqueNumbers.clear();

        //총 3개 만들어서 추천해줘야함
        for(int i = 1; i<=3; i++){
            TravelPlannerListDTO travel = TravelPlannerListDTO.builder()
                    .number(i)
                    .planners(this.makeTravelPlanner(UserInputData))
                    .build();
            TravelPlannerList.add(travel);
        }

        return TravelPlannerList;
    }

    //랜덤으로 중복 안되게 여행지를 골라 각 일수와 여행강도(1,2,3)에 따라 순서를 만들어 루트를 짜줌
    public List<TravelPlannerDTO> makeTravelPlanner(UserInputDTO UserInputData){

        List<TravelPlannerDTO> TravelPlanners = new ArrayList<>();

        int day = Integer.parseInt(UserInputData.getDay());
        int count = 0;

        Random random = new Random();
        
        //다중 if문 구현해야 함 총 14개의 지역이기에 14개 지역으로 나눠지게 됨
        //대구, 대전, 광주, 인천, 제주, 세종(아예 없음), 울산의 경우 데이터가 현저히 적어서 null 값이 잡힐 수 있기 때문에 최소 20개씩 추가해야함
        if(UserInputData.getArea().equals("서울")){
            List<Seoul> SeoulList = this.seoulRepository.findAll();
            //해당 총 서울 관광지 갯수를 가지고 중복이 안되게 랜덤한 숫자를 골름(해당 번호를 가지고 관광지를 골름)
            while ( uniqueNumbers.size() < (Integer.parseInt( UserInputData.getStrength() ) * day * 3) ){
                int randomNumber = random.nextInt(SeoulList.size());
                if (!uniqueNumbers.contains(randomNumber)) { // 중복 제거
                    uniqueNumbers.add(randomNumber);
                }
            }
            List<Integer> uniqueNumber = new ArrayList<>(uniqueNumbers);
            for(int i = 1; i<=day; i++){
                for(int j = 0; j<Integer.parseInt(UserInputData.getStrength()); j++){
                    count++;
                    TravelPlannerDTO TravelPlan = TravelPlannerDTO.builder()
                            .day(String.valueOf(i))
                            .order(count)
                            .touristDestinationName(SeoulList.get(uniqueNumber.get(randomUniqueNumberCount)).getAttractionName())
                            .latitude(SeoulList.get(uniqueNumber.get(randomUniqueNumberCount)).getLatitude())
                            .longitude(SeoulList.get(uniqueNumber.get(randomUniqueNumberCount)).getLongitude())
                            .build();
                    TravelPlanners.add(TravelPlan);
                    randomUniqueNumberCount++;
                }
            }
        }
        else if (UserInputData.getArea().equals("부산")){
            List<Busan> BusanList = this.busanRepository.findAll();
            //해당 총 부산 관광지 갯수를 가지고 중복이 안되게 랜덤한 숫자를 골름(해당 번호를 가지고 관광지를 골름)
            while ( uniqueNumbers.size() < (Integer.parseInt( UserInputData.getStrength() ) * day * 3) ){
                int randomNumber = random.nextInt(BusanList.size());
                if (!uniqueNumbers.contains(randomNumber)) { // 중복 제거
                    uniqueNumbers.add(randomNumber);
                }
            }
            List<Integer> uniqueNumber = new ArrayList<>(uniqueNumbers);
            for(int i = 1; i<=day; i++){
                for(int j = 0; j<Integer.parseInt(UserInputData.getStrength()); j++){
                    count++;
                    TravelPlannerDTO TravelPlan = TravelPlannerDTO.builder()
                            .day(String.valueOf(i))
                            .order(count)
                            .touristDestinationName(BusanList.get(uniqueNumber.get(randomUniqueNumberCount)).getAttractionName())
                            .latitude(BusanList.get(uniqueNumber.get(randomUniqueNumberCount)).getLatitude())
                            .longitude(BusanList.get(uniqueNumber.get(randomUniqueNumberCount)).getLongitude())
                            .build();
                    TravelPlanners.add(TravelPlan);
                    randomUniqueNumberCount++;
                }
            }
        }
        else if (UserInputData.getArea().equals("대구")){
            List<Daegu> DaeguList = this.daeguRepository.findAll();
            //해당 총 대구 관광지 갯수를 가지고 중복이 안되게 랜덤한 숫자를 골름(해당 번호를 가지고 관광지를 골름)
            while ( uniqueNumbers.size() < (Integer.parseInt( UserInputData.getStrength() ) * day * 3) ){
                int randomNumber = random.nextInt(DaeguList.size());
                if (!uniqueNumbers.contains(randomNumber)) { // 중복 제거
                    uniqueNumbers.add(randomNumber);
                }
            }
            List<Integer> uniqueNumber = new ArrayList<>(uniqueNumbers);
            for(int i = 1; i<=day; i++){
                for(int j = 0; j<Integer.parseInt(UserInputData.getStrength()); j++){
                    count++;
                    TravelPlannerDTO TravelPlan = TravelPlannerDTO.builder()
                            .day(String.valueOf(i))
                            .order(count)
                            .touristDestinationName(DaeguList.get(uniqueNumber.get(randomUniqueNumberCount)).getAttractionName())
                            .latitude(DaeguList.get(uniqueNumber.get(randomUniqueNumberCount)).getLatitude())
                            .longitude(DaeguList.get(uniqueNumber.get(randomUniqueNumberCount)).getLongitude())
                            .build();
                    TravelPlanners.add(TravelPlan);
                    randomUniqueNumberCount++;
                }
            }
        }
        else if (UserInputData.getArea().equals("인천")){
            List<Incheon> IncheonList = this.incheonRepository.findAll();
            //해당 총 인천 관광지 갯수를 가지고 중복이 안되게 랜덤한 숫자를 골름(해당 번호를 가지고 관광지를 골름)
            while ( uniqueNumbers.size() < (Integer.parseInt( UserInputData.getStrength() ) * day * 3) ){
                int randomNumber = random.nextInt(IncheonList.size());
                if (!uniqueNumbers.contains(randomNumber)) { // 중복 제거
                    uniqueNumbers.add(randomNumber);
                }
            }
            List<Integer> uniqueNumber = new ArrayList<>(uniqueNumbers);
            for(int i = 1; i<=day; i++){
                for(int j = 0; j<Integer.parseInt(UserInputData.getStrength()); j++){
                    count++;
                    TravelPlannerDTO TravelPlan = TravelPlannerDTO.builder()
                            .day(String.valueOf(i))
                            .order(count)
                            .touristDestinationName(IncheonList.get(uniqueNumber.get(randomUniqueNumberCount)).getAttractionName())
                            .latitude(IncheonList.get(uniqueNumber.get(randomUniqueNumberCount)).getLatitude())
                            .longitude(IncheonList.get(uniqueNumber.get(randomUniqueNumberCount)).getLongitude())
                            .build();
                    TravelPlanners.add(TravelPlan);
                    randomUniqueNumberCount++;
                }
            }
        }
        else if (UserInputData.getArea().equals("광주")){
            List<Gwangju> GawngjuList = this.gwangjuRepository.findAll();
            //해당 총 광주 관광지 갯수를 가지고 중복이 안되게 랜덤한 숫자를 골름(해당 번호를 가지고 관광지를 골름)
            while ( uniqueNumbers.size() < (Integer.parseInt( UserInputData.getStrength() ) * day * 3) ){
                int randomNumber = random.nextInt(GawngjuList.size());
                if (!uniqueNumbers.contains(randomNumber)) { // 중복 제거
                    uniqueNumbers.add(randomNumber);
                }
            }
            List<Integer> uniqueNumber = new ArrayList<>(uniqueNumbers);
            for(int i = 1; i<=day; i++){
                for(int j = 0; j<Integer.parseInt(UserInputData.getStrength()); j++){
                    count++;
                    TravelPlannerDTO TravelPlan = TravelPlannerDTO.builder()
                            .day(String.valueOf(i))
                            .order(count)
                            .touristDestinationName(GawngjuList.get(uniqueNumber.get(randomUniqueNumberCount)).getAttractionName())
                            .latitude(GawngjuList.get(uniqueNumber.get(randomUniqueNumberCount)).getLatitude())
                            .longitude(GawngjuList.get(uniqueNumber.get(randomUniqueNumberCount)).getLongitude())
                            .build();
                    TravelPlanners.add(TravelPlan);
                    randomUniqueNumberCount++;
                }
            }
        }
        else if (UserInputData.getArea().equals("대전")){
            List<Deajeon> DeajeonList = this.deajeonRepository.findAll();
            //해당 총 대전 관광지 갯수를 가지고 중복이 안되게 랜덤한 숫자를 골름(해당 번호를 가지고 관광지를 골름)
            while ( uniqueNumbers.size() < (Integer.parseInt( UserInputData.getStrength() ) * day * 3) ){
                int randomNumber = random.nextInt(DeajeonList.size());
                if (!uniqueNumbers.contains(randomNumber)) { // 중복 제거
                    uniqueNumbers.add(randomNumber);
                }
            }
            List<Integer> uniqueNumber = new ArrayList<>(uniqueNumbers);
            for(int i = 1; i<=day; i++){
                for(int j = 0; j<Integer.parseInt(UserInputData.getStrength()); j++){
                    count++;
                    TravelPlannerDTO TravelPlan = TravelPlannerDTO.builder()
                            .day(String.valueOf(i))
                            .order(count)
                            .touristDestinationName(DeajeonList.get(uniqueNumber.get(randomUniqueNumberCount)).getAttractionName())
                            .latitude(DeajeonList.get(uniqueNumber.get(randomUniqueNumberCount)).getLatitude())
                            .longitude(DeajeonList.get(uniqueNumber.get(randomUniqueNumberCount)).getLongitude())
                            .build();
                    TravelPlanners.add(TravelPlan);
                    randomUniqueNumberCount++;
                }
            }
        }
        else if (UserInputData.getArea().equals("울산")){
            List<Ulsan> UlsanList = this.ulsanRepository.findAll();
            //해당 총 울산 관광지 갯수를 가지고 중복이 안되게 랜덤한 숫자를 골름(해당 번호를 가지고 관광지를 골름)
            while ( uniqueNumbers.size() < (Integer.parseInt( UserInputData.getStrength() ) * day * 3) ){
                int randomNumber = random.nextInt(UlsanList.size());
                if (!uniqueNumbers.contains(randomNumber)) { // 중복 제거
                    uniqueNumbers.add(randomNumber);
                }
            }
            List<Integer> uniqueNumber = new ArrayList<>(uniqueNumbers);
            for(int i = 1; i<=day; i++){
                for(int j = 0; j<Integer.parseInt(UserInputData.getStrength()); j++){
                    count++;
                    TravelPlannerDTO TravelPlan = TravelPlannerDTO.builder()
                            .day(String.valueOf(i))
                            .order(count)
                            .touristDestinationName(UlsanList.get(uniqueNumber.get(randomUniqueNumberCount)).getAttractionName())
                            .latitude(UlsanList.get(uniqueNumber.get(randomUniqueNumberCount)).getLatitude())
                            .longitude(UlsanList.get(uniqueNumber.get(randomUniqueNumberCount)).getLongitude())
                            .build();
                    TravelPlanners.add(TravelPlan);
                    randomUniqueNumberCount++;
                }
            }
        }
        else if (UserInputData.getArea().equals("세종")){
            List<Sejong> SejongList = this.sejongRepository.findAll();
            //해당 총 세종 관광지 갯수를 가지고 중복이 안되게 랜덤한 숫자를 골름(해당 번호를 가지고 관광지를 골름)
            while ( uniqueNumbers.size() < (Integer.parseInt( UserInputData.getStrength() ) * day * 3) ){
                int randomNumber = random.nextInt(SejongList.size());
                if (!uniqueNumbers.contains(randomNumber)) { // 중복 제거
                    uniqueNumbers.add(randomNumber);
                }
            }
            List<Integer> uniqueNumber = new ArrayList<>(uniqueNumbers);
            for(int i = 1; i<=day; i++){
                for(int j = 0; j<Integer.parseInt(UserInputData.getStrength()); j++){
                    count++;
                    TravelPlannerDTO TravelPlan = TravelPlannerDTO.builder()
                            .day(String.valueOf(i))
                            .order(count)
                            .touristDestinationName(SejongList.get(uniqueNumber.get(randomUniqueNumberCount)).getAttractionName())
                            .latitude(SejongList.get(uniqueNumber.get(randomUniqueNumberCount)).getLatitude())
                            .longitude(SejongList.get(uniqueNumber.get(randomUniqueNumberCount)).getLongitude())
                            .build();
                    TravelPlanners.add(TravelPlan);
                    randomUniqueNumberCount++;
                }
            }
        }
        else if (UserInputData.getArea().equals("경기")){
            List<Gyeonggido> GyeonggidoList = this.gyenoggidoRepository.findAll();
            //해당 총 경기 관광지 갯수를 가지고 중복이 안되게 랜덤한 숫자를 골름(해당 번호를 가지고 관광지를 골름)
            while ( uniqueNumbers.size() < (Integer.parseInt( UserInputData.getStrength() ) * day * 3) ){
                int randomNumber = random.nextInt(GyeonggidoList.size());
                if (!uniqueNumbers.contains(randomNumber)) { // 중복 제거
                    uniqueNumbers.add(randomNumber);
                }
            }
            List<Integer> uniqueNumber = new ArrayList<>(uniqueNumbers);
            for(int i = 1; i<=day; i++){
                for(int j = 0; j<Integer.parseInt(UserInputData.getStrength()); j++){
                    count++;
                    TravelPlannerDTO TravelPlan = TravelPlannerDTO.builder()
                            .day(String.valueOf(i))
                            .order(count)
                            .touristDestinationName(GyeonggidoList.get(uniqueNumber.get(randomUniqueNumberCount)).getAttractionName())
                            .latitude(GyeonggidoList.get(uniqueNumber.get(randomUniqueNumberCount)).getLatitude())
                            .longitude(GyeonggidoList.get(uniqueNumber.get(randomUniqueNumberCount)).getLongitude())
                            .build();
                    TravelPlanners.add(TravelPlan);
                    randomUniqueNumberCount++;
                }
            }
        }
        else if (UserInputData.getArea().equals("강원")){
            List<Gangwondo> GangwondoList = this.gangwondoRepository.findAll();
            //해당 총 강원 관광지 갯수를 가지고 중복이 안되게 랜덤한 숫자를 골름(해당 번호를 가지고 관광지를 골름)
            while ( uniqueNumbers.size() < (Integer.parseInt( UserInputData.getStrength() ) * day * 3) ){
                int randomNumber = random.nextInt(GangwondoList.size());
                if (!uniqueNumbers.contains(randomNumber)) { // 중복 제거
                    uniqueNumbers.add(randomNumber);
                }
            }
            List<Integer> uniqueNumber = new ArrayList<>(uniqueNumbers);
            for(int i = 1; i<=day; i++){
                for(int j = 0; j<Integer.parseInt(UserInputData.getStrength()); j++){
                    count++;
                    TravelPlannerDTO TravelPlan = TravelPlannerDTO.builder()
                            .day(String.valueOf(i))
                            .order(count)
                            .touristDestinationName(GangwondoList.get(uniqueNumber.get(randomUniqueNumberCount)).getAttractionName())
                            .latitude(GangwondoList.get(uniqueNumber.get(randomUniqueNumberCount)).getLatitude())
                            .longitude(GangwondoList.get(uniqueNumber.get(randomUniqueNumberCount)).getLongitude())
                            .build();
                    TravelPlanners.add(TravelPlan);
                    randomUniqueNumberCount++;
                }
            }
        }
        else if (UserInputData.getArea().equals("충북")){
            List<Chungbuk> ChungbukList = this.chungbukRepository.findAll();
            //해당 총 충북 관광지 갯수를 가지고 중복이 안되게 랜덤한 숫자를 골름(해당 번호를 가지고 관광지를 골름)
            while ( uniqueNumbers.size() < (Integer.parseInt( UserInputData.getStrength() ) * day * 3) ){
                int randomNumber = random.nextInt(ChungbukList.size());
                if (!uniqueNumbers.contains(randomNumber)) { // 중복 제거
                    uniqueNumbers.add(randomNumber);
                }
            }
            List<Integer> uniqueNumber = new ArrayList<>(uniqueNumbers);
            for(int i = 1; i<=day; i++){
                for(int j = 0; j<Integer.parseInt(UserInputData.getStrength()); j++){
                    count++;
                    TravelPlannerDTO TravelPlan = TravelPlannerDTO.builder()
                            .day(String.valueOf(i))
                            .order(count)
                            .touristDestinationName(ChungbukList.get(uniqueNumber.get(randomUniqueNumberCount)).getAttractionName())
                            .latitude(ChungbukList.get(uniqueNumber.get(randomUniqueNumberCount)).getLatitude())
                            .longitude(ChungbukList.get(uniqueNumber.get(randomUniqueNumberCount)).getLongitude())
                            .build();
                    TravelPlanners.add(TravelPlan);
                    randomUniqueNumberCount++;
                }
            }
        }
        else if (UserInputData.getArea().equals("충남")){
            List<Chungnam> ChungnamList = this.chungnamRepository.findAll();
            //해당 총 충남 관광지 갯수를 가지고 중복이 안되게 랜덤한 숫자를 골름(해당 번호를 가지고 관광지를 골름)
            while ( uniqueNumbers.size() < (Integer.parseInt( UserInputData.getStrength() ) * day * 3) ){
                int randomNumber = random.nextInt(ChungnamList.size());
                if (!uniqueNumbers.contains(randomNumber)) { // 중복 제거
                    uniqueNumbers.add(randomNumber);
                }
            }
            List<Integer> uniqueNumber = new ArrayList<>(uniqueNumbers);
            for(int i = 1; i<=day; i++){
                for(int j = 0; j<Integer.parseInt(UserInputData.getStrength()); j++){
                    count++;
                    TravelPlannerDTO TravelPlan = TravelPlannerDTO.builder()
                            .day(String.valueOf(i))
                            .order(count)
                            .touristDestinationName(ChungnamList.get(uniqueNumber.get(randomUniqueNumberCount)).getAttractionName())
                            .latitude(ChungnamList.get(uniqueNumber.get(randomUniqueNumberCount)).getLatitude())
                            .longitude(ChungnamList.get(uniqueNumber.get(randomUniqueNumberCount)).getLongitude())
                            .build();
                    TravelPlanners.add(TravelPlan);
                    randomUniqueNumberCount++;
                }
            }
        }
        else if (UserInputData.getArea().equals("전북")){
            List<Jeonbuk> JeonbukList = this.jeonbukRepository.findAll();
            //해당 총 전북 관광지 갯수를 가지고 중복이 안되게 랜덤한 숫자를 골름(해당 번호를 가지고 관광지를 골름)
            while ( uniqueNumbers.size() < (Integer.parseInt( UserInputData.getStrength() ) * day * 3) ){
                int randomNumber = random.nextInt(JeonbukList.size());
                if (!uniqueNumbers.contains(randomNumber)) { // 중복 제거
                    uniqueNumbers.add(randomNumber);
                }
            }
            List<Integer> uniqueNumber = new ArrayList<>(uniqueNumbers);
            for(int i = 1; i<=day; i++){
                for(int j = 0; j<Integer.parseInt(UserInputData.getStrength()); j++){
                    count++;
                    TravelPlannerDTO TravelPlan = TravelPlannerDTO.builder()
                            .day(String.valueOf(i))
                            .order(count)
                            .touristDestinationName(JeonbukList.get(uniqueNumber.get(randomUniqueNumberCount)).getAttractionName())
                            .latitude(JeonbukList.get(uniqueNumber.get(randomUniqueNumberCount)).getLatitude())
                            .longitude(JeonbukList.get(uniqueNumber.get(randomUniqueNumberCount)).getLongitude())
                            .build();
                    TravelPlanners.add(TravelPlan);
                    randomUniqueNumberCount++;
                }
            }
        }
        else if (UserInputData.getArea().equals("전남")){
            List<Jeonnam> JeonnamList = this.jeonnamRepository.findAll();
            //해당 총 전남 관광지 갯수를 가지고 중복이 안되게 랜덤한 숫자를 골름(해당 번호를 가지고 관광지를 골름)
            while ( uniqueNumbers.size() < (Integer.parseInt( UserInputData.getStrength() ) * day * 3) ){
                int randomNumber = random.nextInt(JeonnamList.size());
                if (!uniqueNumbers.contains(randomNumber)) { // 중복 제거
                    uniqueNumbers.add(randomNumber);
                }
            }
            List<Integer> uniqueNumber = new ArrayList<>(uniqueNumbers);
            for(int i = 1; i<=day; i++){
                for(int j = 0; j<Integer.parseInt(UserInputData.getStrength()); j++){
                    count++;
                    TravelPlannerDTO TravelPlan = TravelPlannerDTO.builder()
                            .day(String.valueOf(i))
                            .order(count)
                            .touristDestinationName(JeonnamList.get(uniqueNumber.get(randomUniqueNumberCount)).getAttractionName())
                            .latitude(JeonnamList.get(uniqueNumber.get(randomUniqueNumberCount)).getLatitude())
                            .longitude(JeonnamList.get(uniqueNumber.get(randomUniqueNumberCount)).getLongitude())
                            .build();
                    TravelPlanners.add(TravelPlan);
                    randomUniqueNumberCount++;
                }
            }
        }
        else if (UserInputData.getArea().equals("경북")){
            List<Gyongbuk> GyongbukList = this.gyongbukRepository.findAll();
            //해당 총 경북 관광지 갯수를 가지고 중복이 안되게 랜덤한 숫자를 골름(해당 번호를 가지고 관광지를 골름)
            while ( uniqueNumbers.size() < (Integer.parseInt( UserInputData.getStrength() ) * day * 3) ){
                int randomNumber = random.nextInt(GyongbukList.size());
                if (!uniqueNumbers.contains(randomNumber)) { // 중복 제거
                    uniqueNumbers.add(randomNumber);
                }
            }
            List<Integer> uniqueNumber = new ArrayList<>(uniqueNumbers);
            for(int i = 1; i<=day; i++){
                for(int j = 0; j<Integer.parseInt(UserInputData.getStrength()); j++){
                    count++;
                    TravelPlannerDTO TravelPlan = TravelPlannerDTO.builder()
                            .day(String.valueOf(i))
                            .order(count)
                            .touristDestinationName(GyongbukList.get(uniqueNumber.get(randomUniqueNumberCount)).getAttractionName())
                            .latitude(GyongbukList.get(uniqueNumber.get(randomUniqueNumberCount)).getLatitude())
                            .longitude(GyongbukList.get(uniqueNumber.get(randomUniqueNumberCount)).getLongitude())
                            .build();
                    TravelPlanners.add(TravelPlan);
                    randomUniqueNumberCount++;
                }
            }
        }
        else if (UserInputData.getArea().equals("경남")){
            List<Gyeongnam> GyeongnamList = this.gyeongnamRepository.findAll();
            //해당 총 경남 관광지 갯수를 가지고 중복이 안되게 랜덤한 숫자를 골름(해당 번호를 가지고 관광지를 골름)
            while ( uniqueNumbers.size() < (Integer.parseInt( UserInputData.getStrength() ) * day * 3) ){
                int randomNumber = random.nextInt(GyeongnamList.size());
                if (!uniqueNumbers.contains(randomNumber)) { // 중복 제거
                    uniqueNumbers.add(randomNumber);
                }
            }
            List<Integer> uniqueNumber = new ArrayList<>(uniqueNumbers);
            for(int i = 1; i<=day; i++){
                for(int j = 0; j<Integer.parseInt(UserInputData.getStrength()); j++){
                    count++;
                    TravelPlannerDTO TravelPlan = TravelPlannerDTO.builder()
                            .day(String.valueOf(i))
                            .order(count)
                            .touristDestinationName(GyeongnamList.get(uniqueNumber.get(randomUniqueNumberCount)).getAttractionName())
                            .latitude(GyeongnamList.get(uniqueNumber.get(randomUniqueNumberCount)).getLatitude())
                            .longitude(GyeongnamList.get(uniqueNumber.get(randomUniqueNumberCount)).getLongitude())
                            .build();
                    TravelPlanners.add(TravelPlan);
                    randomUniqueNumberCount++;
                }
            }
        }
        else if (UserInputData.getArea().equals("제주")){
            List<Jeju> JejuList = this.jejuRepository.findAll();
            //해당 총 제주 관광지 갯수를 가지고 중복이 안되게 랜덤한 숫자를 골름(해당 번호를 가지고 관광지를 골름)
            while ( uniqueNumbers.size() < (Integer.parseInt( UserInputData.getStrength() ) * day * 3) ){
                int randomNumber = random.nextInt(JejuList.size());
                if (!uniqueNumbers.contains(randomNumber)) { // 중복 제거
                    uniqueNumbers.add(randomNumber);
                }
            }
            List<Integer> uniqueNumber = new ArrayList<>(uniqueNumbers);
            for(int i = 1; i<=day; i++){
                for(int j = 0; j<Integer.parseInt(UserInputData.getStrength()); j++){
                    count++;
                    TravelPlannerDTO TravelPlan = TravelPlannerDTO.builder()
                            .day(String.valueOf(i))
                            .order(count)
                            .touristDestinationName(JejuList.get(uniqueNumber.get(randomUniqueNumberCount)).getAttractionName())
                            .latitude(JejuList.get(uniqueNumber.get(randomUniqueNumberCount)).getLatitude())
                            .longitude(JejuList.get(uniqueNumber.get(randomUniqueNumberCount)).getLongitude())
                            .build();
                    TravelPlanners.add(TravelPlan);
                    randomUniqueNumberCount++;
                }
            }
        }
        return TravelPlanners;
    }
}
