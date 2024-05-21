package com.riwi.continental.infrastructure.abstract_services;

import com.riwi.continental.api.dto.request.CustomerRequest;
import com.riwi.continental.api.dto.response.CustomerResponse;
import com.riwi.continental.domain.entities.Customer;

public interface ICustomerService extends IBaseService<CustomerRequest, CustomerResponse, String> {
  public Customer getUser(String token);
}
