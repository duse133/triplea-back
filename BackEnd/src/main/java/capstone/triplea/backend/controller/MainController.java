package capstone.triplea.backend.controller;

import capstone.triplea.backend.dto.*;
import capstone.triplea.backend.exception.CParameterNotFound;
import capstone.triplea.backend.service.GetTourListService;
import capstone.triplea.backend.service.MakePlannerService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MakePlannerService makePlannerService;
    private final GetTourListService getTourListService;

    // 해당 지역의 모든 여행지 정보넘겨줌(여행이름, 소개글)
    @GetMapping("/api/tourlist")
    public List<TourListDTO> getAreaTourList(@RequestParam String area){
        return this.getTourListService.getAreaTourList(area);
    }
    
    // 군집으로 만들고 최단거리 알고리즘을 통해 여행 루트 생성
    @GetMapping("/api/planners")
    public TravelPlannerListDTO makePlanners(UserInputDTO userInputDTO){
        if(userInputDTO.getArea() == null || userInputDTO.getDay() == null){
            throw new CParameterNotFound();
        }
        TravelPlannerListDTO TravelPlannerList = new TravelPlannerListDTO();
        TravelPlannerList.setPlanners(this.makePlannerService.makePlanners(userInputDTO));

        TravelPlannerList.setCode("S01");
        TravelPlannerList.setMsg("성공");

        return TravelPlannerList;
    }

    //관광공사 api 이미지 가져오기
//    @GetMapping("/getImage")
//    public String getImage() throws JSONException {
//        // URL of the REST API
//        String apiUrl = "https://apis.data.go.kr/B551011/PhotoGalleryService1/gallerySearchList1?serviceKey=tvxLAkccgL4jGXY8xkf%2BW1D6ERENvDeWS6zyfwUFACQBQ2xAhKhUEhBq4QXCbVcy46GuPss%2B%2B88PrtzwY3csNw%3D%3D&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&arrange=A&keyword=%EB%8C%80%EA%B5%AC%EC%88%98%EB%AA%A9%EC%9B%90&_type=json";
//
//        // Create RestTemplate instance
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Send GET request to the API and retrieve response as String
//        String jsonResponse = restTemplate.getForObject(apiUrl, String.class);
//
//        System.out.println(jsonResponse);
//        // Parse the JSON response to extract the image URL
//        String imageUrl = parseJsonResponse(jsonResponse);
//
//        // Now you have the image URL, you can further process it (e.g., download the image)
//        // For simplicity, let's just return the image URL
//        return imageUrl;
//    }

    // Method to parse JSON response and extract the image URL
    private String parseJsonResponse(String jsonResponse) {
        // Parse JSON response to extract image URL
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONObject response = jsonObject.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        System.out.println(body.getString("items"));
        JSONObject items = body.getJSONObject("items");
        JSONArray itemArray = items.getJSONArray("item");

        // Assuming there is only one item in the array, getting the first one
        JSONObject item = itemArray.getJSONObject(0);

        // Extracting the image URL
        String imageUrl = item.getString("galWebImageUrl");

        return imageUrl;
    }

    //게시판 DB에 저장할 게시판 제목, 게시판내용인 여행루트, 비밀번호(암호화 시켜야함)
    //이미 게시판에 있는 정보이면 그 비밀번호와 수정할 정보의 비밀번호와 비교하여 맞으면 수정 틀리면 비밀번호 틀렸다고 오류메세지를 보냄
    @PostMapping("/api/notice_boards")
    public void insertBoard(){
        
    }

    //게시판 DB에 저장되어있는 정보를 제거
    @DeleteMapping("/api/notice_boards")
    public void deleteBoard(){

    }
    
    //게시판에 저장되어 있는 모든 정보를 가져와야함
    @GetMapping("/api/notice_boards")
    public void getBoard(){

    }
}