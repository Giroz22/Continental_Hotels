package com.riwi.continental.api.dto.request;


import com.riwi.continental.util.enums.StateRoom;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {
    @NotBlank(message = "The state of the room is required")
    @Enumerated(EnumType.STRING)
    private StateRoom status;

    @NotNull( message = "The room number is requaried")
    @Min(value = 0)
    @PositiveOrZero(message = "The room number must be positive or greater then zero")
    private int num;

    @NotNull(message = "The room price is requaried")
    @PositiveOrZero(message = "The price must be positive or greater then zero")
    private double price;
    
    @NotNull(message = "The type room is requaried")
    private String roomType_id;

}
