package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ReserveRegister {

    private String reservationStatus; // 예약; 취소;

    private String reservationName;
    private String reservationPhoneNumber;
    private long amount;
    private UUID performanceId;
    private int round;
    private char line;
    private int seat;

}
