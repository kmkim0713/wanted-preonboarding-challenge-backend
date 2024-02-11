package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    public Performance getPerformanceUUID(String performanceName) {
        return performanceRepository.findByName(performanceName);
    }


    public boolean reserve(ReserveInfo reserveInfo) {
        log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId());
        Performance info = performanceRepository.findById(reserveInfo.getPerformanceId())
                .orElseThrow(EntityNotFoundException::new);
        String enableReserve = info.getIsReserve();
        if (enableReserve.equalsIgnoreCase("enable")) {
            // 1. 결제
            long pay = reserveInfo.getAmount() - info.getPrice();
            if (pay >= 0) {
                reserveInfo.setAmount(pay);
                // 2. 예매 진행
                reservationRepository.save(Reservation.of(reserveInfo));
                return true;
            }
        }

        // 예약 비용 부족
        return false;
    }

    public ResponseEntity<ResponseHandler<Map<String, Object>>> reserveResponse(Boolean resultFlag, ReserveInfo reserveInfo) {
        if (resultFlag) {
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("round", reserveInfo.getRound());
            responseData.put("performanceName", reserveInfo.getReservationName());
            responseData.put("line", reserveInfo.getLine());
            responseData.put("seat", reserveInfo.getSeat());
            responseData.put("performanceId", reserveInfo.getPerformanceId());
            responseData.put("reservationName", reserveInfo.getReservationName());
            responseData.put("reservationPhoneNumber", reserveInfo.getReservationPhoneNumber());

            ResponseHandler<Map<String, Object>> responseHandler = ResponseHandler.<Map<String, Object>>builder()
                    .statusCode(HttpStatus.OK)
                    .message("예약 성공")
                    .data(responseData)
                    .build();
            return ResponseEntity.ok(responseHandler);

        } else {
            ResponseHandler<Map<String, Object>> responseHandler = ResponseHandler.<Map<String, Object>>builder()
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .message("예약 비용 부족")
                    .data(null)
                    .build();
            return ResponseEntity.ok(responseHandler);
        }
    }

}