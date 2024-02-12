package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveRegisterByPerformanceName;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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

    @PostMapping("/register/performanceName")
    public String register(@RequestBody ReserveRegisterByPerformanceName reserveRegisterByPerformanceName) {
        System.out.println("Reservation Name: " + reserveRegisterByPerformanceName.getReservationName());
        System.out.println("Reservation Phone Number: " + reserveRegisterByPerformanceName.getReservationPhoneNumber());
        System.out.println("Amount: " + reserveRegisterByPerformanceName.getAmount());
        System.out.println("Performance Name: " + reserveRegisterByPerformanceName.getPerformanceName());
        System.out.println("Round: " + reserveRegisterByPerformanceName.getRound());
        System.out.println("Line: " + reserveRegisterByPerformanceName.getLine());
        System.out.println("Seat: " + reserveRegisterByPerformanceName.getSeat());

        return "OK";
    }
    
}