package com.riwi.continental.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.continental.api.dto.request.CustomerRequest;
import com.riwi.continental.api.dto.response.CustomerResponse;
import com.riwi.continental.domain.entities.Booking;
import com.riwi.continental.domain.entities.Customer;
import com.riwi.continental.domain.repositories.CustomerRepository;
import com.riwi.continental.infrastructure.abstract_services.ICustomerService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerService implements ICustomerService{

  @Autowired
  private final CustomerRepository customerRepository;

  @Override
  public Page<CustomerResponse> getAll(int page, int size) {
    if(page < 0) page = 0;

    PageRequest pagination = PageRequest.of(page, size);
    return this.customerRepository.findAll(pagination).map(customer -> this.entityToResponse(customer));
  }

  @Override
  public CustomerResponse findById(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public CustomerResponse create(CustomerRequest entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Override
  public CustomerResponse update(CustomerRequest entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void delete(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  private CustomerResponse entityToResponse(Customer entity){
    CustomerResponse response = new CustomerResponse();
    BeanUtils.copyProperties(entity, response);
    //response.setBookings(entity.getBookings().stream().map(booking -> this.bookingToResponse(booking)).collect(collectors.toList()));
    return response;
  }

  // private BookingToCustomerResponse bookingToResponse (Booking entity) {
  //   BookingToCustomerResponse response = new BookingToCustomerResponse ();

  //   BeanUtils.copyProperties(entity, response);

  //   return response;
  // }

  //debe sarlir una lista en booking todas las reservas que tiene el cliente


}
