package capstone.triplea.backend.service;

import capstone.triplea.backend.dto.ResponseCode;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

@Service
public class GetImageResource {

    public ResponseCode ApiImageSource(String keyword) throws UnsupportedEncodingException {
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
        
        //해당 이미지를 못찾을 시에
        if(imageSourceUrl == null){
            responseCode.setCode("E10");
            responseCode.setMsg("해당 이미지를 찾을 수 없음");
            return responseCode;
        }

        responseCode.setCode("S01");
        responseCode.setMsg("성공");
        responseCode.setUrl(imageSourceUrl);

        return responseCode;
    }

}
