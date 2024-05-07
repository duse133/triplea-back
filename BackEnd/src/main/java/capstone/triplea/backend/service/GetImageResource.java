package capstone.triplea.backend.service;

import capstone.triplea.backend.dto.ResponseCode;
import capstone.triplea.backend.exception.CApiConnectionError;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class GetImageResource {
    public ResponseCode ApiImageSource(String keyword) throws IOException {

        ResponseCode responseCode = new ResponseCode();
        // 키워드를 URL 인코딩
        String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");

        // API 요청 URL 생성
        String apiUrl = String.format("https://apis.data.go.kr/B551011/PhotoGalleryService1/gallerySearchList1?serviceKey=tvxLAkccgL4jGXY8xkf%%2BW1D6ERENvDeWS6zyfwUFACQBQ2xAhKhUEhBq4QXCbVcy46GuPss%%2B%%2B88PrtzwY3csNw%%3D%%3D&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&arrange=A&keyword=%s&_type=json", encodedKeyword);

        StringBuilder result = new StringBuilder();

        URL url = new URL(apiUrl);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

        String returnLine;
        while ((returnLine = br.readLine()) != null) {
            result.append(returnLine + "\n\r");
        }
        urlConnection.disconnect();

        String galWebImageUrl = null;

        //외부 공공 데이터 api 통신 오류 발생시
        if(result.isEmpty()){
            throw new CApiConnectionError();
        }
        try {
            // JSON 데이터를 ObjectMapper를 사용하여 JsonNode로 변환
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(result.toString());

            int totalCount = rootNode
                    .path("response")
                    .path("body")
                    .path("totalCount")
                    .asInt();

            if(totalCount == 0){
                responseCode.setCode("E10");
                responseCode.setMsg("해당 이미지를 찾을 수 없음");
                return responseCode;
            }

            // "galWebImageUrl" 필드 값 추출
            galWebImageUrl = rootNode
                    .path("response")
                    .path("body")
                    .path("items")
                    .path("item")
                    .get(0)
                    .path("galWebImageUrl")
                    .asText();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(galWebImageUrl.isEmpty()){
            responseCode.setCode("E10");
            responseCode.setMsg("해당 이미지를 찾을 수 없음");
            return responseCode;
        }

        responseCode.setCode("E00");
        responseCode.setMsg("성공");
        responseCode.setUrl(galWebImageUrl);
        return responseCode;
    }
}
