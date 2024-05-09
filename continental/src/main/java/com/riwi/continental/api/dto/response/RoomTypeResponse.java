package com.riwi.continental.api.dto.response;

import java.math.BigDecimal;
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
    private String id;    
    private String name;
    private String description;
    private BigDecimal baseValue;
    private List<RoomToAny> rooms;
}
