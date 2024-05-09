package com.riwi.continental.api.dto.response;


import com.riwi.continental.util.enums.StateRoom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private String id;
    private StateRoom state;
    private int roomNum;
    private double price;
    private int capicity;
    private String description;
    private RoomTypeToAnyResponse roomType;
    private FloorToHotelResponse floor;
}
