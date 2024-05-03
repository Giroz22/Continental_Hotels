package com.riwi.continental.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FloorRequest {

  private int number;

  private int numberOfRooms;

  private String hotelId;
}
