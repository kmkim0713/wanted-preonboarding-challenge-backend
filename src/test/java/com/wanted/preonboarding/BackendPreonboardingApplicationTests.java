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
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    @Autowired
    private TicketSeller ticketSeller;

    @Test
    void contextLoads() {
    }

    @Test
    void registerTest() throws JsonProcessingException, Exception {

        UUID performanceId = ticketSeller.getPerformanceUUID("김경민의 서커스").getId();

        ReserveInfo reserveInfo = ReserveInfo.builder()
                .performanceId(performanceId)
                .reservationName("김경민")
                .reservationPhoneNumber("01011112222")
                .reservationStatus("예약")
                .amount(10000)
                .round(1)
                .line('K')
                .seat(12)
                .build();

        
        mockMvc.perform(post("/reserve/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reserveInfo)));

        // resultActions.andExpect(status().isOk());

        }

    @Test
    void tempRegisterTest() throws JsonProcessingException, Exception {
        String url = "http://localhost:8016/reserve/temp/register";
        
        UUID performanceId = ticketSeller.getPerformanceUUID("김경민의 서커스").getId();
        
        mockMvc.perform(post(url)
                .param("performanceId", performanceId.toString())
                .param("reservationName", "김경민")
                .param("reservationPhoneNumber", "01011112222")
                .param("reservationStatus", "예약")
                .param("amount", "10000")
                .param("round", "1")
                .param("line", "K")
                .param("seat", "12"))
                .andExpect(status().isOk());
    }




}