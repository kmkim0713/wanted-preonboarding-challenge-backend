package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.wanted.preonboarding.core.exception.InsufficientBalanceException;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("null")
@Service
@Slf4j
@RequiredArgsConstructor
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private long totalAmount = 0L;

    public List<PerformanceInfo> getAllPerformanceInfoList() {
        return performanceRepository.findByIsReserve("enable")
            .stream()
            .map(PerformanceInfo::of)
            .toList();
    }

    public PerformanceInfo getPerformanceInfoDetail(String name) {
        return PerformanceInfo.of(performanceRepository.findByName(name));
    }

    public boolean reserve(ReserveInfo reserveInfo) {
        log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId());
        Performance info = performanceRepository.findById(reserveInfo.getPerformanceId())
            .orElseThrow(EntityNotFoundException::new);
        String enableReserve = info.getIsReserve();
        if (enableReserve.equalsIgnoreCase("enable")) {
            // 1. 결제
            int price = info.getPrice();
            reserveInfo.setAmount(reserveInfo.getAmount() - price);
            // 2. 예매 진행
            reservationRepository.save(Reservation.of(reserveInfo));
            return true;

        } else {
            return false;
        }
    }


    /** 새로 작성 **/
    //  공연 이름, id로 예약
    public ReserveResponse register(ReserveInfo reserveInfo) throws InsufficientBalanceException{

        Performance performance = null;
        
        // 공연정보 확인
        if(reserveInfo.getPerformanceId() != null){
            performance = performanceRepository.findById(reserveInfo.getPerformanceId())
            .orElseThrow(EntityNotFoundException::new);
        } else if (reserveInfo.getPerformanceId() == null) {
            performance = performanceRepository.findOptionalByName(reserveInfo.getPerformanceName())
            .orElseThrow(EntityNotFoundException::new);

            reserveInfo.setPerformanceId(performance.getId());
        }

        // 예약자 확인
        // 이미 존재하는 예약자인지 확인이 필요함

        
        System.out.println("Reservation Name: " + performance.getName());

        String enableReserve = performance.getIsReserve();
        if (enableReserve.equalsIgnoreCase("enable")) {
            
            // 결제
            int price = performance.getPrice();
            
            if (reserveInfo.getAmount() - price >= 0){ // 잔고에서 공연값 빼기
                reserveInfo.setAmount(reserveInfo.getAmount() - price);
            } else if (reserveInfo.getAmount() - price < 0) { // 잔고 부족
                throw new InsufficientBalanceException("잔고가 부족합니다");
            }

            // 예매 진행
            reservationRepository.save(Reservation.of(reserveInfo));

            ReserveResponse reserveResponse = ReserveResponse.builder()
                .reservationName(reserveInfo.getReservationName())
                .reservationPhoneNumber(reserveInfo.getReservationPhoneNumber())
                .performanceId(reserveInfo.getPerformanceId()) 
                .performanceName(reserveInfo.getPerformanceName())
                .round(reserveInfo.getRound())
                .line(reserveInfo.getLine()) 
                .seat(reserveInfo.getSeat()) 
                .build();

            return reserveResponse;

        } else {
            throw new InsufficientBalanceException("예약이 마감된 공연입니다");
        }

    }

}