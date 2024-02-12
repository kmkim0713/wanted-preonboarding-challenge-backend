package com.wanted.preonboarding.ticket.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReserveInfo {
    // 공연 및 전시 정보 + 예약자 정보
    // private UUID performanceId;
    // private String reservationName;
    // private String reservationPhoneNumber;
    // private String reservationStatus; // 예약; 취소;
    // private long amount;
    // private int round;
    // private char line;
    // private int seat;

        
    private String reservationName; // 고객 이름
    private String reservationPhoneNumber; // 고객 휴대전화
    private long amount; // 결제 가능 금액
    private UUID performanceId; // 예약을 원하는 공연 id
    private String performanceName; // 예약을 원하는 공연 이름
    private String reservationStatus; // 예약; 취소;
    private int round; // 회차
    private char line; // 좌석 열
    private int seat; // 좌석 숫자
}