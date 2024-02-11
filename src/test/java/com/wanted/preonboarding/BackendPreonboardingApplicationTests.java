package com.wanted.preonboarding;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;

import java.util.HashMap;
import java.util.Map;
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
    void registerTest() throws JsonProcessingException, Exception {
        
        ReserveInfo reserveInfo = ReserveInfo.builder()
                .reservationName("김경민")
                .reservationPhoneNumber("01011112222")
                .reservationStatus("예약")
                .amount(10000)
                .round(1)
                .line('K')
                .seat(12)
                .build();

        
        ResultActions resultActions = mockMvc.perform(post("/reserve/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reserveInfo)));

            resultActions.andExpect(status().isOk());

        }



}