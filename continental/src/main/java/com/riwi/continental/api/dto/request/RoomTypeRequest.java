package com.riwi.continental.api.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    private double baseValue;

    @NotNull(message = "The Type room value is requaried")
    @Min(value = 1, message = "The capacity must ve grater than zero" )
    @Max(value = 15, message = "The capacity exceeds the maximum of fifteen persons")@Digits(integer = 2, fraction = 0, message = "The number is not valid")
    private int capicity;

}
