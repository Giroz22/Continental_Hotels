package com.riwi.continental.api.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelRequest {

  @NotEmpty(message = "Type the name of the hotel")
  private String name;

  @NotEmpty(message = "You must put a location")
  private String location;

  @NotEmpty(message = "You must put the contact info")
  private String contact;

  @Min(value = 0, message = "The min calification is 0")
  @Max(value = 5, message = "The max calification is 5")
  @Digits(integer = 1, fraction = 1)
  private double calification;

  @Min(value = 1, message = "The minimum amount of floors is 1")
  @Max(value = 30, message = "The maximum amount of floors is 30")
  private int numberOfFloors;

}
