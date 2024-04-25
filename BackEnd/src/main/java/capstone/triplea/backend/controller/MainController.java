package capstone.triplea.backend.controller;

import capstone.triplea.backend.dto.PointDTO;
import capstone.triplea.backend.dto.TourListDTO;
import capstone.triplea.backend.dto.TravelPlannerListDTO;
import capstone.triplea.backend.dto.UserInputDTO;
import capstone.triplea.backend.exception.CParameterNotFound;
import capstone.triplea.backend.service.GetTourListService;
import capstone.triplea.backend.service.MakePlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MakePlannerService makePlannerService;
    private final GetTourListService getTourListService;

    @GetMapping("/api/planner")
    public List<TravelPlannerListDTO> makePlanner(UserInputDTO userInputDTO){
        if(userInputDTO.getArea() == null || userInputDTO.getStrength() == null || userInputDTO.getDay() == null){
            throw new CParameterNotFound();
        }
        List<TravelPlannerListDTO> planners = this.makePlannerService.makePlannerTourist(userInputDTO);

        // 이미 만들어진 리스트에서 code 부분을 수정
        for (TravelPlannerListDTO planner : planners) {
            planner.setCode("S01");
            planner.setMsg("성공");
        }
        return planners;
    }

    @GetMapping("/api/tourlist")
    public List<TourListDTO> getAreaTourList(@RequestParam String area){
        return this.getTourListService.getAreaTourList(area);
    }
    
//    // 군집 알고리즘 테스트용
//    @GetMapping("/test")
//    public List<PointDTO> test(){
//        return this.makePlannerService.makeClustering();
//    }
}