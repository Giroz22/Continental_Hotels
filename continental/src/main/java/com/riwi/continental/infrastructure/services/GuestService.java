package com.riwi.continental.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.continental.api.dto.request.GuestRequest;
import com.riwi.continental.api.dto.response.BookingToGuestResponse;
import com.riwi.continental.api.dto.response.GuestResponse;
import com.riwi.continental.domain.entities.Guest;
import com.riwi.continental.domain.repositories.BookingRepository;
import com.riwi.continental.domain.repositories.GuestRepository;
import com.riwi.continental.infrastructure.abstract_services.IGuestService;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuestService  implements IGuestService{

  @Autowired
  private final GuestRepository guestRepository;

  //@Autowired
  //private final BookingRepository bookingRepository;

  @Override
  public Page<GuestResponse> getAll(int page, int size) {
    if (page<0) page =0;

    PageRequest pagination = PageRequest.of(page, size);
    return this.guestRepository.findAll(pagination).map(guest -> this.entityToResponse(guest));
  }

  @Override
  public GuestResponse findById(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public GuestResponse create(GuestRequest entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Override
  public GuestResponse update(GuestRequest entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void delete(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  private GuestResponse entityToResponse(Guest entity){
    GuestResponse response = new GuestResponse();
    BeanUtils.copyProperties(entity, response);

    //BookingToGuestResponse bookingDTO = new BookingToGuestResponse();

    //BeanUtils.copyProperties(entity.getBookings, bookingDTO);

    //response.setBooking(bookingDTO);
    return response;
  }
}
