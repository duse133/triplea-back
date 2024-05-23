package capstone.triplea.backend.controller;

import capstone.triplea.backend.dto.*;
import capstone.triplea.backend.exception.CParameterNotFound;
import capstone.triplea.backend.exception.CWrongPWError;
import capstone.triplea.backend.service.GetImageResource;
import capstone.triplea.backend.service.GetTourListService;
import capstone.triplea.backend.service.MakePlannerService;
import capstone.triplea.backend.service.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MakePlannerService makePlannerService;
    private final GetTourListService getTourListService;
    private final GetImageResource getImageResource;
    private final NoticeBoardService noticeBoardService;

    // 해당 지역의 모든 여행지 정보넘겨줌(여행이름, 소개글)
    @GetMapping("/api/tourlist")
    public List<TourListDTO> getAreaTourList(@RequestParam String area) {
        return this.getTourListService.getAreaTourList(area);
    }

    // 군집으로 만들고 최단거리 알고리즘을 통해 여행 루트 생성
    @GetMapping("/api/planners")
    public TravelPlannerListDTO makePlanners(UserInputDTO userInputDTO) {
        if (userInputDTO.getArea() == null || userInputDTO.getDay() == null || userInputDTO.getAccommodationName() == null) {
            throw new CParameterNotFound();
        }
        TravelPlannerListDTO TravelPlannerList = new TravelPlannerListDTO();
        TravelPlannerList.setPlanners(this.makePlannerService.makePlanners(userInputDTO));

        TravelPlannerList.setCode("S01");
        TravelPlannerList.setMsg("성공");

        return TravelPlannerList;
    }

    //관광공사 api 이미지 가져오기
    @GetMapping("api/getImageSource")
    public ResponseCode getImage(@RequestParam String keyword) throws UnsupportedEncodingException {
        return getImageResource.ImageSource(keyword);
    }

    //게시판 DB에 저장할 게시판 제목, 게시판내용인 여행루트, 비밀번호(암호화 시켜야함)
    //이미 게시판에 있는 정보이면 그 비밀번호와 수정할 정보의 비밀번호와 비교하여 맞으면 수정 틀리면 비밀번호 틀렸다고 오류메세지를 보냄
    @PostMapping("/api/notice_boards")
    public void insertBoard(@RequestBody InputNoticeBoardDTO inputNoticeBoardDTO) {
        noticeBoardService.makeNotice(inputNoticeBoardDTO.getTitle(), inputNoticeBoardDTO.getDate(), inputNoticeBoardDTO.getContents(), inputNoticeBoardDTO.getPassword());
    }

    //게시판 DB에 저장되어있는 정보를 제거
    @DeleteMapping("/api/notice_boards/{id}")
    public void deleteBoard(@PathVariable int id) {
        noticeBoardService.deleteNotice(id);
    }
    //게시판에 저장되어 있는 모든 정보를 가져와야함
    @GetMapping("/api/notice_boards")
    public List<NoticeBoardDTO> getBoard() {
        return noticeBoardService.getNoticeBoard();
    }

    //게시판에 저장되어 있는 데이터 수정
    @PutMapping("/api/notice_boards/{id}")
    public ResponseEntity<ResponseCode> updateBoard(@PathVariable int id, @RequestBody InputNoticeBoardDTO inputNoticeBoardDTO){
        noticeBoardService.updateNotice(id, inputNoticeBoardDTO);
        return ResponseEntity.ok().build();
    }

    //게시판 정보 수정, 삭제시 비밀번호 확인 매핑
    @GetMapping("/api/notice_boards/{id}/verify")
    private void VerifyPassword(@PathVariable int id, @RequestParam String password) {
        if (!noticeBoardService.verifyPassword(id, password)) {
            throw new CWrongPWError();
        }
    }
}