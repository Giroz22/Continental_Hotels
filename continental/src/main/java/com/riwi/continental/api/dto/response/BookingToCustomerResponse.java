package com.riwi.continental.api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.riwi.continental.util.enums.StatusBooking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingToCustomerResponse {
  private String id;
  private BigDecimal price;
  private StatusBooking status;
  private LocalDate admissionDate;
  private LocalDate departureDate;
  private LocalTime admissionTime;
  private LocalTime departureTime;
  private List<GuestToBookingResponse> guests;
  private List<RoomToBookingResponse> rooms;

}
