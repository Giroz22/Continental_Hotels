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
public class RoomToRoomTypeResponse {
    private String room_id;
    private StateRoom state;
    private int num;
    private double price;
}
