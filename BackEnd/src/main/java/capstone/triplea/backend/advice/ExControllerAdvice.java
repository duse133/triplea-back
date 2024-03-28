package capstone.triplea.backend.advice;

import capstone.triplea.backend.dto.TravelPlanerListDTO;
import capstone.triplea.backend.exception.CParameterBound;
import capstone.triplea.backend.exception.CParameterNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CParameterNotFound.class)
    public ResponseEntity<TravelPlanerListDTO> errorNotFoundException() {
        TravelPlanerListDTO errorResult = TravelPlanerListDTO.builder().
                code("E01").
                msg("필수 파라미터 누락").
                build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler(CParameterBound.class)
    public ResponseEntity<TravelPlanerListDTO> errorIndexBoundException() {
        TravelPlanerListDTO errorResult = TravelPlanerListDTO.builder().
                code("E02").
                msg("데이터 부족, 강도와 일수를 더 낮추십시오.").
                build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }
}
