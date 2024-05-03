package com.riwi.continental.api.dto.request.Hotel_Floor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelRequest {

  private String name;

  private String location;

  private String contact;

  private double calification;

  private int numberOfFloors;

}
