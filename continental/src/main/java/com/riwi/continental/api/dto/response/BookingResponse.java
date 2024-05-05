package com.riwi.continental.api.dto.response;

import com.riwi.continental.domain.entities.Booking;
import com.riwi.continental.util.enums.StatusBooking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse<LocalDate> {
    
    private Double valor;
    private StatusBooking estado;
    private LocalDate fecha_ingreso;
    private LocalDate fecha_salida;
    private Booking booking;
    
}
