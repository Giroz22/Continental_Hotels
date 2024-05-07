package com.riwi.continental.api.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import com.riwi.continental.util.enums.StatusBooking;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {


    private Double price;
    // @NotBlank(message = "The admision date is required")
    private LocalDate admissionDate;
    // @NotBlank(message = "The departure date is required")
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalTime admissionTime;
    private StatusBooking status;
    
}
