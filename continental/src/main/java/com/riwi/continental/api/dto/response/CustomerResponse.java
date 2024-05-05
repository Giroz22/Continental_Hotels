package com.riwi.continental.api.dto.response;

import java.util.List;

import com.riwi.continental.domain.entities.Booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
  private String id;
  private String name;
  private String lastname;
  private int age;
  private String idDocument;
  private String cellphone;
  //private List<BookingToCustomerResponse> booking;
}
