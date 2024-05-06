package com.riwi.continental.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
  private String name;
  private String lastname;
  private int age;
  private String idDocument;
  private String cellphone;
}
