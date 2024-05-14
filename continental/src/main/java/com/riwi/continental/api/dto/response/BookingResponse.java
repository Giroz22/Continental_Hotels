package com.riwi.continental.api.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.riwi.continental.domain.entities.Booking;
import com.riwi.continental.domain.entities.Customer;
import com.riwi.continental.util.enums.StatusBooking;

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
    private Double price;
    private StatusBooking status;
    private LocalDate admissionDate;
    private LocalDate departureDate;
    private LocalTime admissionTime;
    private LocalTime departureTime;
    private RoomToAny roomResponse;
    private GuestToBookingResponse guestResponse;
    private CustomerToBookingResponse customerResponse;
    
    
    
}
