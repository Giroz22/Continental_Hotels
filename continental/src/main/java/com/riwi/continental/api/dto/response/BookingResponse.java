package com.riwi.continental.api.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.riwi.continental.util.enums.StatusBooking;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private String id;
    private LocalDate admissionDate;
    private LocalDate departureDate;
    private LocalTime admissionTime;
    private LocalTime departureTime;
    private BigDecimal price;
    private StatusBooking status;
    private CustomerToBookingResponse customer;
    private List<GuestToBookingResponse> guests;
    private List<RoomToBookingResponse> rooms;
}
