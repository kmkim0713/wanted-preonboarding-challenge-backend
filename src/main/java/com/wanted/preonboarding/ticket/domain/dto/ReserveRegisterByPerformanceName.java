package com.wanted.preonboarding.ticket.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveRegisterByPerformanceName {

    private String reservationName; // 고객 이름
    private String reservationPhoneNumber; // 고객 휴대전화
    private long amount; // 결제 가능 금액
    private String performanceName; // 예약을 원하는 공연 이름
    private int round; // 회차
    private char line; // 좌석 열
    private int seat; // 좌석 숫자
}
