package com.riwi.continental.api.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponse {
  private String id;
  private String name;
  private String location;
  private String contact;
  private double calification;
  private double earnings;
  private int numberOfFloors;
  private List<FloorToHotelResponse> floors;
}
