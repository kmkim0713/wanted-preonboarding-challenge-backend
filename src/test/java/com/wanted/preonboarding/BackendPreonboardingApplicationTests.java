package com.wanted.preonboarding;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("null")
@AutoConfigureMockMvc
@SpringBootTest
class BackendPreonboardingApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void reserveRegister() throws JsonProcessingException, Exception {
        String url = "http://localhost:8016/reserve/register";

        ReserveInfo reserveInfo = ReserveInfo.builder()
        .reservationName("김경민")
        .reservationPhoneNumber("01011112222")
        .amount(100000L)
        .performanceName("김경민의 서커스")
        .reservationStatus("예약")
        .round(1)
        .line('K')
        .seat(12)
        .build();
        
        mockMvc.perform(post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(reserveInfo)))
        .andExpect(status().isOk());
    }


}