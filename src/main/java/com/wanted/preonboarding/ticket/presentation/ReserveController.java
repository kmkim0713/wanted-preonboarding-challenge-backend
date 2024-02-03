package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import lombok.RequiredArgsConstructor;
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
    public boolean register(@RequestBody ReserveInfo reserveInfo) {
        System.out.println("[ register ]");

        UUID performanceId = ticketSeller.getPerformanceUUID("김경민의 서커스").getId();

        return ticketSeller.reserve(ReserveInfo.builder()
            .performanceId(performanceId)
            .reservationName(reserveInfo.getReservationName())
            .reservationPhoneNumber(reserveInfo.getReservationPhoneNumber())
            .reservationStatus(reserveInfo.getReservationStatus())
            .amount(reserveInfo.getAmount())
            .round(reserveInfo.getRound())
            .line(reserveInfo.getLine())
            .seat(reserveInfo.getSeat())
            .build()
        );
    }




}
