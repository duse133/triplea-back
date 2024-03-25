package capstone.triplea.backend.controller;

import capstone.triplea.backend.dto.TravelPlanerListDTO;
import capstone.triplea.backend.dto.UserInputDTO;
import capstone.triplea.backend.service.MakePlanerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MakePlanerService makePlanerService;

    @GetMapping("/make/planer")
    public List<TravelPlanerListDTO> makePlaner(@RequestParam String area,
                                                @RequestParam String day,
                                                @RequestParam String strength){

        UserInputDTO UserInputData = UserInputDTO.builder().
                area(area).
                day(day).
                strength(strength).
                build();

        return this.makePlanerService.makePlanerTourist(UserInputData);
    }


}
