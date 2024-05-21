package com.riwi.continental.api.dto.request;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypeRequest {
    @Size(min = 0, max = 45, message = "Name exceeds the allowed number of characters")
    @NotBlank(message = "The Type room name is requaried")
    private String name;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "The Type room description is requaried")
    private String description;

    @NotNull(message = "The Type room value is requaried")
    @PositiveOrZero(message = "The base value must ve grater than zero")
    private BigDecimal baseValue;
}
