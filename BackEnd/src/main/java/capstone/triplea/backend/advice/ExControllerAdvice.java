package capstone.triplea.backend.advice;

import capstone.triplea.backend.dto.ResponseCode;
import capstone.triplea.backend.dto.TravelPlannerListDTO;
import capstone.triplea.backend.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CParameterNotFound.class)
    public ResponseEntity<TravelPlannerListDTO> errorNotFoundException() {
        TravelPlannerListDTO errorResult = TravelPlannerListDTO.builder().
                code("E01").
                msg("필수 파라미터 누락").
                build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler(CParameterBound.class)
    public ResponseEntity<TravelPlannerListDTO> errorIndexBoundException() {
        TravelPlannerListDTO errorResult = TravelPlannerListDTO.builder().
                code("E02").
                msg("데이터 부족, 일수를 더 낮추십시오.").
                build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler(CApiConnectionError.class)
    public ResponseEntity<ResponseCode> errorApiConnectionException() {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode("E03");
        responseCode.setMsg("외부 API 통신 오류");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCode);
    }

    @ExceptionHandler(CWrongPWError.class)
    public ResponseEntity<ResponseCode> errorWrongPasswordException() {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode("E04");
        responseCode.setMsg("비밀번호가 틀렸습니다.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseCode);
    }

    @ExceptionHandler(CNoticeNotFound.class)
    public ResponseEntity<ResponseCode> errorNoticeNotFoundException(){
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode("E05");
        responseCode.setMsg("게시물 정보를 찾을 수 없습니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCode);
    }
}
