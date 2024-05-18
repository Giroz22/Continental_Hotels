package com.riwi.continental.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.continental.api.dto.request.GuestRequest;
import com.riwi.continental.api.dto.response.BookingToGuestResponse;
import com.riwi.continental.api.dto.response.CustomerToBookingResponse;
import com.riwi.continental.api.dto.response.GuestResponse;
import com.riwi.continental.domain.entities.Booking;
import com.riwi.continental.domain.entities.Customer;
import com.riwi.continental.domain.entities.Guest;
import com.riwi.continental.domain.repositories.BookingRepository;
import com.riwi.continental.domain.repositories.GuestRepository;
import com.riwi.continental.infrastructure.abstract_services.IGuestService;
import com.riwi.continental.util.enums.AgeCategory;
import com.riwi.continental.util.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuestService implements IGuestService {

  @Autowired
  private final GuestRepository guestRepository;

  @Autowired
  private final BookingRepository bookingRepository;

  @Override
  public Page<GuestResponse> getAll(int page, int size) {
    if (page < 0)
      page = 0;

    PageRequest pagination = PageRequest.of(page, size);
    return this.guestRepository.findAll(pagination).map(this::entityToResponse);
  }

  @Override
  public GuestResponse findById(String id) {
    return this.entityToResponse(this.find(id));
  }

  @Override
  public GuestResponse create(GuestRequest request) {
    Booking booking = this.bookingRepository.findById(request.getBookingId())
        .orElseThrow(() -> new IdNotFoundException("Booking"));
    Guest guest = this.requestToGuest(request, new Guest());
    guest.setBooking(booking);

    return this.entityToResponse(this.guestRepository.save(guest));
  }

  @Override
  public GuestResponse update(GuestRequest request, String id) {
    Guest guest = this.find(id);

    Booking booking = this.bookingRepository.findById(request.getBookingId())
        .orElseThrow(() -> new IdNotFoundException("Booking"));

    this.requestToGuest(request, guest);
    guest.setBooking(booking);
    guest.setAgeCategory(request.getAgeCategory());

    return this.entityToResponse(this.guestRepository.save(guest));
  }

  @Override
  public void delete(String id) {
    Guest guest = this.find(id);
    this.guestRepository.delete(guest);
  }

  private GuestResponse entityToResponse(Guest entity) {
    GuestResponse response = new GuestResponse();
    BeanUtils.copyProperties(entity, response);

    BookingToGuestResponse bookingDTO = new BookingToGuestResponse();

    BeanUtils.copyProperties(entity.getBooking(), bookingDTO);

    bookingDTO.setCustomer(this.customerToCustomerToBookingResponse(entity.getBooking().getCustomer()));

    response.setBooking(bookingDTO);

    return response;
  }

  private Guest requestToGuest(GuestRequest request, Guest entity) {
    entity.setIdDocument(request.getIdDocument());
    entity.setName(request.getName());
    entity.setLastname(request.getLastname());
    entity.setAge(request.getAge());
    entity.setAgeCategory(AgeCategory.ADULT);

    return entity;
  }

  private Guest find(String id) {
    return this.guestRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Guest"));
  }

  private CustomerToBookingResponse customerToCustomerToBookingResponse(Customer customer) {
    CustomerToBookingResponse customerToBookingResponse = new CustomerToBookingResponse();
    BeanUtils.copyProperties(customer, customerToBookingResponse);

    return customerToBookingResponse;

  }

}
