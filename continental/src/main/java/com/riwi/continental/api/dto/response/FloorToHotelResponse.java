package com.riwi.continental.api.dto.response;

//import java.util.List;

import com.riwi.continental.util.enums.StatusFloor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FloorToHotelResponse {
    private String id;
    private int number;
    private int numberOfRooms;
    private StatusFloor statusFloor;
    // private List<Room> rooms;
}
