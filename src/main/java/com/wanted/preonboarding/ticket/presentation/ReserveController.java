package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.exception.InsufficientBalanceException;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveResponse;

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

    @PostMapping("/register")
    public ReserveResponse register(@RequestBody ReserveInfo reserveInfo) throws InsufficientBalanceException {
        
        System.out.println("[ 예약 진행 ]");
        return ticketSeller.register(reserveInfo);
    }
    
}

