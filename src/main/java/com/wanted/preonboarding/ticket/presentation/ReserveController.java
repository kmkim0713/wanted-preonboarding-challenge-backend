package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final TicketSeller ticketSeller;

    @PostMapping("/")
    public boolean reservation() {
        System.out.println("reservation");

        return ticketSeller.reserve(ReserveInfo.builder()
            .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
            .reservationName("유진호")
            .reservationPhoneNumber("010-1234-1234")
            .reservationStatus("reserve")
            .amount(200000)
            .round(1)
            .line('A')
            .seat(1)
            .build()
        );
    }

    // 예약 시스템
    @PostMapping("/register")
    public ResponseEntity<ResponseHandler<Map<String, Object>>> register(@RequestBody ReserveInfo reserveInfo) {
        System.out.println("[ register ]");

        UUID performanceId = ticketSeller.getPerformanceUUID("김경민의 서커스").getId();

        return ticketSeller.reserveResponse(ticketSeller.reserve(ReserveInfo.builder()
                .performanceId(performanceId)
                .reservationName(reserveInfo.getReservationName())
                .reservationPhoneNumber(reserveInfo.getReservationPhoneNumber())
                .reservationStatus(reserveInfo.getReservationStatus())
                .amount(reserveInfo.getAmount())
                .round(reserveInfo.getRound())
                .line(reserveInfo.getLine())
                .seat(reserveInfo.getSeat())
                .build()), reserveInfo);
    }





    @PostMapping("/temp/register")
    public ResponseEntity<ResponseHandler<Map<String, Object>>> tempRegister(
            @RequestParam("reservationName") String reservationName,
            @RequestParam("reservationPhoneNumber") String reservationPhoneNumber,
            @RequestParam("reservationStatus") String reservationStatus,
            @RequestParam("amount") String amount,
            @RequestParam("round") String round,
            @RequestParam("line") String line,
            @RequestParam("seat") String seat) {

        System.out.println("[ register ]");

        UUID performanceId = ticketSeller.getPerformanceUUID("김경민의 서커스").getId();

        ReserveInfo reserveInfo = ReserveInfo.builder()
                .performanceId(performanceId)
                .reservationName(reservationName)
                .reservationPhoneNumber(reservationPhoneNumber)
                .reservationStatus(reservationStatus)
                .amount(Long.valueOf(amount))
                .round(Integer.valueOf(round))
                .line(line.charAt(0))  // char로 변환
                .seat(Integer.parseInt(seat))
                .build();

        return ticketSeller.reserveResponse(
                ticketSeller.reserve(reserveInfo), reserveInfo);
    }


}









