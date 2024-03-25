package capstone.triplea.backend.controller;

import capstone.triplea.backend.dto.TravelPlanerListDTO;
import capstone.triplea.backend.dto.UserInputDTO;
import capstone.triplea.backend.service.MakePlanerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MakePlanerService makePlanerService;

    @PostMapping("/make/planer")
    public List<TravelPlanerListDTO> makePlaner(@RequestBody UserInputDTO UserInputData){
        return this.makePlanerService.makePlanerTourist(UserInputData);
    }
}
