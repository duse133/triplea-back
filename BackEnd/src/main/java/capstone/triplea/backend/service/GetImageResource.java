package capstone.triplea.backend.service;

import capstone.triplea.backend.dto.ResponseCode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.el.util.MessageFactory.get;

@Service
public class GetImageResource {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ResponseCode ImageSource(String keyword) throws UnsupportedEncodingException {
        ResponseCode responseCode = new ResponseCode();
        // 키워드를 URL 인코딩
        String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
        String imageSourceUrl = null;
        // 한국관광공사 사진갤러리 크롤링
        String url = "https://phoko.visitkorea.or.kr/media/mediaList.kto?keyword=" + encodedKeyword;
        try {
            // Connect to the website and fetch the HTML content
            Document doc = Jsoup.connect(url).get();

            // Select the HTML elements containing image URLs
            Elements imageElements = doc.select("img[src]");
            // Iterate through the image elements and print the image source URLs
            for (Element imageElement : imageElements) {
                // Get the image source URL
                String imageUrl = imageElement.attr("src");
                // Print the image source URL
                if (imageUrl.startsWith("https://conlab.visitkorea.or.kr/api/depot/public/depot-flow/query/download-image/")) {
                    imageSourceUrl = imageUrl;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //해당 이미지를 못찾을 시에 naver api 이미지 검색을 이용하여 한번 더 찾음
        if(imageSourceUrl == null){
            String naverSearch = this.ImageSearchNaver(keyword);
            if(naverSearch.isEmpty()){
                responseCode.setCode("E10");
                responseCode.setMsg("해당 이미지를 찾을 수 없음");
                return responseCode;
            }else{
                responseCode.setCode("S01");
                responseCode.setMsg("성공");
                responseCode.setUrl(naverSearch);
                return responseCode;
            }
        }

        responseCode.setCode("S01");
        responseCode.setMsg("성공");
        responseCode.setUrl(imageSourceUrl);

        return responseCode;
    }

    public String ImageSearchNaver(String keyword) {
        String url = "https://openapi.naver.com/v1/search/image?query=" + keyword;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", "UIG5KfxVJ0LBJ3epYQ1X");
        headers.set("X-Naver-Client-Secret", "KqVXtIWzP_");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);


        if (!response.getStatusCode().is2xxSuccessful()) {
            return "";
        }

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode items = root.path("items");
            if (items.isArray()) {
                for (JsonNode item : items) {
                    String title = item.path("title").asText().toLowerCase();
                    String link = item.path("link").asText();
                    if (title.contains(keyword.toLowerCase())) {
                        return link;
                    }
                }
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
