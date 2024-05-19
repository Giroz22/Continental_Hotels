package com.riwi.continental.api.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

    @NotNull(message = "The admision date is required")
    @FutureOrPresent(message = "The admision date can't be in the past")
    private LocalDate admissionDate;

    @NotNull(message = "The departure date is required")
    @Future(message = "The departure date can't be in the past")
    private LocalDate departureDate;

    @NotNull(message = "The customer id is required")
    private String customerId;

    @NotNull(message = "The rooms id are required")
    private List<String> listRoomId;
}
