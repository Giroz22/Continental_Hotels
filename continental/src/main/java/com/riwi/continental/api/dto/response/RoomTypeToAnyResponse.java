package com.riwi.continental.api.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypeToAnyResponse {
    private String id;    
    private String name;
    private String description;
    private BigDecimal baseValue;
    private int capicity;
}
