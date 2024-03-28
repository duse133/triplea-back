package capstone.triplea.backend.service;

import capstone.triplea.backend.dto.TravelPlanerDTO;
import capstone.triplea.backend.dto.TravelPlanerListDTO;
import capstone.triplea.backend.dto.UserInputDTO;
import capstone.triplea.backend.entity.Seoul;
import capstone.triplea.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MakePlanerService {

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

    //사용자가 입력한 4개의 데이터(지역, 여행일수, 여행강도, 테마)에 따라 3개의 랜덤한 관광지를 연결지어 여행 루틴을 만들어주는 로직 구현 예정
    //나중에 여행 강도에 따른 로직을 구현 예정
    public List<TravelPlanerListDTO> makePlanerTourist(UserInputDTO UserInputData){
        List<TravelPlanerListDTO> TravelPlanerList = new ArrayList<>();

        //총 3개 만들어서 추천해줘야함
        for(int i = 1; i<=3; i++){
            TravelPlanerListDTO travel = TravelPlanerListDTO.builder()
                    .number(i)
                    .planers(this.makeTravelPlaner(UserInputData))
                    .build();
            TravelPlanerList.add(travel);
        }

        return TravelPlanerList;
    }

    //테스트용 여행 리스트(추후에 여기다가 여행 일 수에 맞춰 랜덤으로 여행 루틴을 만들어주는 로직 구현 예정,중복 제외 로직은 나중에 추가 예정)
    public List<TravelPlanerDTO> makeTravelPlaner(UserInputDTO UserInputData){

        List<TravelPlanerDTO> TravelPlaners = new ArrayList<>();
        TravelPlanerDTO TravelPlan = TravelPlanerDTO.builder()
                .day("")
                .order("")
                .touristDestinationName("")
                .latitude(1.1)
                .longitude(1.1)
                .build();
        TravelPlaners.add(TravelPlan);
        return TravelPlaners;
    }

    //DB 불러와지는지 테스트용 메소드
    public List<TravelPlanerDTO> testPlaner(){
        List<TravelPlanerDTO> TravelPlaners = new ArrayList<>();
        List<Seoul> seoulList = this.seoulRepository.findAll();

        for (int i = 0; i< seoulList.size(); i++) {
            TravelPlanerDTO TravelPlan = TravelPlanerDTO.builder()
                    .day("1")
                    .order("1")
                    .touristDestinationName(seoulList.get(i).getAttractionName())
                    .latitude(seoulList.get(i).getLatitude())
                    .longitude(seoulList.get(i).getLongitude())
                    .build();
            TravelPlaners.add(TravelPlan);
        }
        return TravelPlaners;
    }
}
