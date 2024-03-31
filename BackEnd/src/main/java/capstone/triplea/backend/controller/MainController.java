package capstone.triplea.backend.controller;

import capstone.triplea.backend.dto.TravelPlannerListDTO;
import capstone.triplea.backend.dto.UserInputDTO;
import capstone.triplea.backend.exception.CParameterNotFound;
import capstone.triplea.backend.service.MakePlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MakePlannerService makePlannerService;

    @GetMapping("/api/planner")
    public List<TravelPlannerListDTO> makePlanner(UserInputDTO userInputDTO){
        if(userInputDTO.getArea() == null || userInputDTO.getStrength() == null || userInputDTO.getDay() == null){
            throw new CParameterNotFound();
        }
        return this.makePlannerService.makePlannerTourist(userInputDTO);
    }
}
