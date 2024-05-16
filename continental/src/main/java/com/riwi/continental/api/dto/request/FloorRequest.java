package com.riwi.continental.api.dto.request;

import java.util.List;

import com.riwi.continental.api.dto.response.RoomToAny;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FloorRequest {

  @NotNull(message = "You hace to specify the number of the floor")
  @Min(value = 1, message = "The min floor is the first")
  @Max(value = 30, message = "The last floor must be the 30")
  private int number;

  @NotNull(message = "You have to specify the number of rooms the floor have")
  @Min(value = 5, message = "Min amount of rooms is 5 per floor")
  @Max(value = 20, message = "Max amount of rooms is 20 per floor")
  private int numberOfRooms;

  @NotNull
  private String hotelId;

}
