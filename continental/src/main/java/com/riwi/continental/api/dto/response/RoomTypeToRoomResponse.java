package com.riwi.continental.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypeToRoomResponse {
    private String roomType_id;    
    private String name;
    private String description;
    private double baseValue;
    private int capicity;
}
