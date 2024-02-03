package com.wanted.preonboarding;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BackendPreonboardingApplicationTests {


    private RestTemplate restTemplate;

    @Autowired
    public BackendPreonboardingApplicationTests(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Test
    void postTest() {
        String url = "http://localhost:8016/reserve/register";

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("reservationName", "손나현");
        requestData.put("reservationPhoneNumber", "01011112222");
        requestData.put("reservationStatus", "예약");
        requestData.put("amount", "100000");
        requestData.put("round", "1");
        requestData.put("line", "K");
        requestData.put("seat", "12");

        // HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        // 응답 코드 확인
        assertEquals(200, responseEntity.getStatusCodeValue());

        // 응답 본문 확인 (필요에 따라 추가)
        String responseBody = responseEntity.getBody();

        System.out.println(responseBody);

    }


    @Test
    void getTest() {
        String url = "http://localhost:8016/query/all/performance";

        String requestData = "";

        // HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // HttpEntity 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(requestData, headers);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        // 응답 코드 확인
        assertEquals(200, responseEntity.getStatusCodeValue());

        // 응답 본문 확인 (필요에 따라 추가)
        String responseBody = responseEntity.getBody();

        System.out.println(responseBody);

    }

}
