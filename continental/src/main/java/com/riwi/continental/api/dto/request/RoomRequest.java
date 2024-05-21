package com.riwi.continental.api.dto.request;

import java.math.BigDecimal;

import com.riwi.continental.util.enums.StateRoom;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
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
public class RoomRequest {
    // @NotBlank(message = "The state of the room is required")
    @Enumerated(EnumType.STRING)
    private StateRoom state;

    @NotNull(message = "The room number is required")
    @PositiveOrZero(message = "The room number must be positive or greater then zero")
    private int roomNum;

    @NotNull(message = "The room price is required")
    @PositiveOrZero(message = "The price must be positive or greater then zero")
    private BigDecimal price;

    @NotNull(message = "The room capacity is required")
    @Min(value = 1, message = "The capicity of the room must be more than one person")
    @Max(value = 15, message = "The capacity of the room must be less than fifteen persons")
    @Digits(integer = 2, fraction = 0, message = "The number is not valid")
    private int capicity;

    @NotNull(message = "The room description is required")
    @Lob
    private String description;

    @Size(min = 0, max = 36)
    @NotBlank(message = "The room type is required")
    private String roomTypeId;

    @Size(min = 0, max = 36)
    @NotBlank(message = "The room floor is required")
    private String floorId;

    
}
