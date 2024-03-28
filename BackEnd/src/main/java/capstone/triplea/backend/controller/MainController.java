package capstone.triplea.backend.controller;

import capstone.triplea.backend.dto.TravelPlanerListDTO;
import capstone.triplea.backend.dto.UserInputDTO;
import capstone.triplea.backend.exception.CParameterNotFound;
import capstone.triplea.backend.service.MakePlanerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MakePlanerService makePlanerService;

    @GetMapping("/api/planner")
    public List<TravelPlanerListDTO> makePlaner(UserInputDTO userInputDTO){
        if(userInputDTO.getArea() == null || userInputDTO.getStrength() == null || userInputDTO.getDay() == null){
            throw new CParameterNotFound();
        }
        return this.makePlanerService.makePlanerTourist(userInputDTO);
    }
}
