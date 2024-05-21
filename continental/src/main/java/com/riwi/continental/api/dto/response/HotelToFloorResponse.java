package com.riwi.continental.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelToFloorResponse {
    private String id;
    private String name;
    private String location;
    private String contact;
    private double qualification;
    private int numberOfFloors;
}
