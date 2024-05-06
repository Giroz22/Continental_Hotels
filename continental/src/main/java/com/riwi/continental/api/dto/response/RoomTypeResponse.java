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
public class RoomTypeResponse {
    private String roomType_id;    
    private String name;
    private String description;
    private double baseValue;
    private int capicity;
    private List<RoomToRoomTypeResponse> rooms;
}
