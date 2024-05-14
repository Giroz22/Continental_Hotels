package com.riwi.continental.api.dto.request;

import com.riwi.continental.util.enums.AgeCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestRequest {
  @NotBlank(message = "The id document is required")
  @Size(min = 3, max = 20, message = "The id document exceeds the numbers of characters allowed")
  private String idDocument;
  @Size(min = 3, max = 40, message = "The name exceeds the numbers of characters allowed")
  @NotBlank(message = "name is required")
  private String name;
  @Size(min = 3, max = 40, message = "The lastname exceeds the numbers of characters allowed")
  @NotBlank(message = "The lastname is required")
  private String lastname;
  @NotNull(message = "The age is required")
  private int age;
  @NotNull(message = "The age category is required")
  private AgeCategory ageCategory;
  @NotBlank(message = "The booking id is required")
  private String BookingId;

}
