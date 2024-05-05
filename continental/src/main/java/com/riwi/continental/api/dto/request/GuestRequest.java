package com.riwi.continental.api.dto.request;

import com.riwi.continental.util.enums.AgeCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestRequest {
  private String name;
  private String lastname;
  private int age;
  private AgeCategory ageCategory;
  private String BookingId;

}
