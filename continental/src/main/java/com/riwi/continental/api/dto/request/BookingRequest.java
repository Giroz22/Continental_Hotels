package com.riwi.continental.api.dto.request;

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
public class BookingRequest<LocalDate> {


    @NotBlank(message = "La fecha de ingreso es requerida")
    private LocalDate fecha_ingreso;
    @NotBlank(message = "La fecha de salida es requerida")
    private LocalDate fecha_salida;
    
}
