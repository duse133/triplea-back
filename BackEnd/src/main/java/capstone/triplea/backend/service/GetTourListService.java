package capstone.triplea.backend.service;

import capstone.triplea.backend.dto.TourListDTO;
import capstone.triplea.backend.entity.*;
import capstone.triplea.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTourListService {
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

    public List<TourListDTO> getAreaTourList(String area){
        List<TourListDTO> Error = new ArrayList<>();

        if(area.isEmpty()){
            Error.add(TourListDTO.builder().code("E01").msg("필수 파라미터 누락").build());
            return Error;
        }

        if(area.equals("서울")){
            List<Seoul> SeoulList = this.seoulRepository.findAll();
            List<TourListDTO> tourList = new ArrayList<>();
            for (Seoul seoul : SeoulList) {
                TourListDTO tourListInfo = TourListDTO.builder()
                        .code("S01")
                        .msg("성공")
                        .touristDestinationName(seoul.getAttractionName())
                        .longitude(seoul.getLongitude())
                        .latitude(seoul.getLatitude())
                        .introduction(seoul.getIntroduction())
                        .address(seoul.getMap_name_address())
                        .build();
                tourList.add(tourListInfo);
            }
            return tourList;
        }
        else if(area.equals("부산")){
            List<Busan> BusanList = this.busanRepository.findAll();
            List<TourListDTO> tourList = new ArrayList<>();
            for (Busan busan : BusanList) {
                TourListDTO tourListInfo = TourListDTO.builder()
                        .code("S01")
                        .msg("성공")
                        .touristDestinationName(busan.getAttractionName())
                        .longitude(busan.getLongitude())
                        .latitude(busan.getLatitude())
                        .introduction(busan.getIntroduction())
                        .address(busan.getMap_name_address())
                        .build();
                tourList.add(tourListInfo);
            }
            return tourList;
        }
        else if(area.equals("충남")){
            List<Chungnam> ChungnamList = this.chungnamRepository.findAll();
            List<TourListDTO> tourList = new ArrayList<>();
            for (Chungnam chungnam : ChungnamList) {
                TourListDTO tourListInfo = TourListDTO.builder()
                        .code("S01")
                        .msg("성공")
                        .touristDestinationName(chungnam.getAttractionName())
                        .longitude(chungnam.getLongitude())
                        .latitude(chungnam.getLatitude())
                        .introduction(chungnam.getIntroduction())
                        .address(chungnam.getMap_name_address())
                        .build();
                tourList.add(tourListInfo);
            }
            return tourList;
        }
        else if(area.equals("충북")){
            List<Chungbuk> ChungbukList = this.chungbukRepository.findAll();
            List<TourListDTO> tourList = new ArrayList<>();
            for (Chungbuk chungbuk : ChungbukList) {
                TourListDTO tourListInfo = TourListDTO.builder()
                        .code("S01")
                        .msg("성공")
                        .touristDestinationName(chungbuk.getAttractionName())
                        .longitude(chungbuk.getLongitude())
                        .latitude(chungbuk.getLatitude())
                        .introduction(chungbuk.getIntroduction())
                        .address(chungbuk.getMap_name_address())
                        .build();
                tourList.add(tourListInfo);
            }
            return tourList;
        }
        else if(area.equals("대구")){
            List<Daegu> BusanList = this.daeguRepository.findAll();
            List<TourListDTO> tourList = new ArrayList<>();
            for (Daegu daegu : BusanList) {
                TourListDTO tourListInfo = TourListDTO.builder()
                        .code("S01")
                        .msg("성공")
                        .touristDestinationName(daegu.getAttractionName())
                        .longitude(daegu.getLongitude())
                        .latitude(daegu.getLatitude())
                        .introduction(daegu.getIntroduction())
                        .address(daegu.getMap_name_address())
                        .build();
                tourList.add(tourListInfo);
            }
            return tourList;
        }
        else if(area.equals("대전")){
            List<Deajeon> DaejeonList = this.deajeonRepository.findAll();
            List<TourListDTO> tourList = new ArrayList<>();
            for (Deajeon deajeon : DaejeonList) {
                TourListDTO tourListInfo = TourListDTO.builder()
                        .code("S01")
                        .msg("성공")
                        .touristDestinationName(deajeon.getAttractionName())
                        .longitude(deajeon.getLongitude())
                        .latitude(deajeon.getLatitude())
                        .introduction(deajeon.getIntroduction())
                        .address(deajeon.getMap_name_address())
                        .build();
                tourList.add(tourListInfo);
            }
            return tourList;
        }
        else if(area.equals("강원도")){
            List<Gangwondo> GangwondoList = this.gangwondoRepository.findAll();
            List<TourListDTO> tourList = new ArrayList<>();
            for (Gangwondo gangwondo : GangwondoList) {
                TourListDTO tourListInfo = TourListDTO.builder()
                        .code("S01")
                        .msg("성공")
                        .touristDestinationName(gangwondo.getAttractionName())
                        .longitude(gangwondo.getLongitude())
                        .latitude(gangwondo.getLatitude())
                        .introduction(gangwondo.getIntroduction())
                        .address(gangwondo.getMap_name_address())
                        .build();
                tourList.add(tourListInfo);
            }
            return tourList;
        }
        else if(area.equals("광주")){
            List<Gwangju> GwangjuList = this.gwangjuRepository.findAll();
            List<TourListDTO> tourList = new ArrayList<>();
            for (Gwangju gwangju : GwangjuList) {
                TourListDTO tourListInfo = TourListDTO.builder()
                        .code("S01")
                        .msg("성공")
                        .touristDestinationName(gwangju.getAttractionName())
                        .longitude(gwangju.getLongitude())
                        .latitude(gwangju.getLatitude())
                        .introduction(gwangju.getIntroduction())
                        .address(gwangju.getMap_name_address())
                        .build();
                tourList.add(tourListInfo);
            }
            return tourList;
        }
        else if(area.equals("경기도")){
            List<Gyeonggido> GyeonggidoList = this.gyenoggidoRepository.findAll();
            List<TourListDTO> tourList = new ArrayList<>();
            for (Gyeonggido gyeonggido : GyeonggidoList) {
                TourListDTO tourListInfo = TourListDTO.builder()
                        .code("S01")
                        .msg("성공")
                        .touristDestinationName(gyeonggido.getAttractionName())
                        .longitude(gyeonggido.getLongitude())
                        .latitude(gyeonggido.getLatitude())
                        .introduction(gyeonggido.getIntroduction())
                        .address(gyeonggido.getMap_name_address())
                        .build();
                tourList.add(tourListInfo);
            }
            return tourList;
        }
        else if(area.equals("경남")){
            List<Gyeongnam> GyeongnamList = this.gyeongnamRepository.findAll();
            List<TourListDTO> tourList = new ArrayList<>();
            for (Gyeongnam gyeongnam : GyeongnamList) {
                TourListDTO tourListInfo = TourListDTO.builder()
                        .code("S01")
                        .msg("성공")
                        .touristDestinationName(gyeongnam.getAttractionName())
                        .longitude(gyeongnam.getLongitude())
                        .latitude(gyeongnam.getLatitude())
                        .introduction(gyeongnam.getIntroduction())
                        .address(gyeongnam.getMap_name_address())
                        .build();
                tourList.add(tourListInfo);
            }
            return tourList;
        }
        else if(area.equals("경북")){
            List<Gyongbuk> GyongbukList = this.gyongbukRepository.findAll();
            List<TourListDTO> tourList = new ArrayList<>();
            for (Gyongbuk gyongbuk : GyongbukList) {
                TourListDTO tourListInfo = TourListDTO.builder()
                        .code("S01")
                        .msg("성공")
                        .touristDestinationName(gyongbuk.getAttractionName())
                        .longitude(gyongbuk.getLongitude())
                        .latitude(gyongbuk.getLatitude())
                        .introduction(gyongbuk.getIntroduction())
                        .address(gyongbuk.getMap_name_address())
                        .build();
                tourList.add(tourListInfo);
            }
            return tourList;
        }
        else if(area.equals("인천")){
            List<Incheon> IncheonList = this.incheonRepository.findAll();
            List<TourListDTO> tourList = new ArrayList<>();
            for (Incheon incheon : IncheonList) {
                TourListDTO tourListInfo = TourListDTO.builder()
                        .code("S01")
                        .msg("성공")
                        .touristDestinationName(incheon.getAttractionName())
                        .longitude(incheon.getLongitude())
                        .latitude(incheon.getLatitude())
                        .introduction(incheon.getIntroduction())
                        .address(incheon.getMap_name_address())
                        .build();
                tourList.add(tourListInfo);
            }
            return tourList;
        }
        else if(area.equals("제주")){
            List<Jeju> JejuList = this.jejuRepository.findAll();
            List<TourListDTO> tourList = new ArrayList<>();
            for (Jeju jeju : JejuList) {
                TourListDTO tourListInfo = TourListDTO.builder()
                        .code("S01")
                        .msg("성공")
                        .touristDestinationName(jeju.getAttractionName())
                        .longitude(jeju.getLongitude())
                        .latitude(jeju.getLatitude())
                        .introduction(jeju.getIntroduction())
                        .address(jeju.getMap_name_address())
                        .build();
                tourList.add(tourListInfo);
            }
            return tourList;
        }
        else if(area.equals("전북")){
            List<Jeonbuk> JeonbukList = this.jeonbukRepository.findAll();
            List<TourListDTO> tourList = new ArrayList<>();
            for (Jeonbuk jeonbuk : JeonbukList) {
                TourListDTO tourListInfo = TourListDTO.builder()
                        .code("S01")
                        .msg("성공")
                        .touristDestinationName(jeonbuk.getAttractionName())
                        .longitude(jeonbuk.getLongitude())
                        .latitude(jeonbuk.getLatitude())
                        .introduction(jeonbuk.getIntroduction())
                        .address(jeonbuk.getMap_name_address())
                        .build();
                tourList.add(tourListInfo);
            }
            return tourList;
        }
        else if(area.equals("전남")){
            List<Jeonnam> JeonnamList = this.jeonnamRepository.findAll();
            List<TourListDTO> tourList = new ArrayList<>();
            for (Jeonnam jeonnam : JeonnamList) {
                TourListDTO tourListInfo = TourListDTO.builder()
                        .code("S01")
                        .msg("성공")
                        .touristDestinationName(jeonnam.getAttractionName())
                        .longitude(jeonnam.getLongitude())
                        .latitude(jeonnam.getLatitude())
                        .introduction(jeonnam.getIntroduction())
                        .address(jeonnam.getMap_name_address())
                        .build();
                tourList.add(tourListInfo);
            }
            return tourList;
        }
        else if(area.equals("세종")){
            List<Sejong> SejongList = this.sejongRepository.findAll();
            List<TourListDTO> tourList = new ArrayList<>();
            for (Sejong sejong : SejongList) {
                TourListDTO tourListInfo = TourListDTO.builder()
                        .code("S01")
                        .msg("성공")
                        .touristDestinationName(sejong.getAttractionName())
                        .longitude(sejong.getLongitude())
                        .latitude(sejong.getLatitude())
                        .introduction(sejong.getIntroduction())
                        .address(sejong.getMap_name_address())
                        .build();
                tourList.add(tourListInfo);
            }
            return tourList;
        }
        else if(area.equals("울산")){
            List<Ulsan> UlsanList = this.ulsanRepository.findAll();
            List<TourListDTO> tourList = new ArrayList<>();
            for (Ulsan ulsan : UlsanList) {
                TourListDTO tourListInfo = TourListDTO.builder()
                        .code("S01")
                        .msg("성공")
                        .touristDestinationName(ulsan.getAttractionName())
                        .longitude(ulsan.getLongitude())
                        .latitude(ulsan.getLatitude())
                        .introduction(ulsan.getIntroduction())
                        .address(ulsan.getMap_name_address())
                        .build();
                tourList.add(tourListInfo);
            }
            return tourList;
        }

        Error.add(TourListDTO.builder().code("E03").msg("해당하는 지역 정보가 없습니다.").build());
        return  Error;
    }

}
