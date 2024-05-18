package com.riwi.continental.infrastructure.services;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.continental.api.dto.request.CustomerRequest;
import com.riwi.continental.api.dto.response.BookingToCustomerResponse;
import com.riwi.continental.api.dto.response.CustomerResponse;
import com.riwi.continental.api.dto.response.FloorToAny;
import com.riwi.continental.api.dto.response.GuestToBookingResponse;
import com.riwi.continental.api.dto.response.RoomToBookingResponse;
import com.riwi.continental.api.dto.response.RoomTypeToAnyResponse;
import com.riwi.continental.domain.entities.Booking;
import com.riwi.continental.domain.entities.Customer;
import com.riwi.continental.domain.entities.Floor;
import com.riwi.continental.domain.entities.Guest;
import com.riwi.continental.domain.entities.Room;
import com.riwi.continental.domain.entities.RoomType;
import com.riwi.continental.domain.repositories.CustomerRepository;
import com.riwi.continental.infrastructure.abstract_services.ICustomerService;
import com.riwi.continental.util.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerService implements ICustomerService {

  @Autowired
  private final CustomerRepository customerRepository;

  @Override
  public Page<CustomerResponse> getAll(int page, int size) {
    if (page < 0)
      page = 0;

    PageRequest pagination = PageRequest.of(page, size);
    return this.customerRepository.findAll(pagination).map(this::entityToResponse);
  }

  @Override
  public CustomerResponse findById(String id) {
    Customer customer = this.find(id);
    return this.entityToResponse(customer);
  }

  @Override
  public CustomerResponse create(CustomerRequest request) {
    Customer customer = this.requestToEntity(request, new Customer());
    return this.entityToResponse(this.customerRepository.save(customer));
  }

  @Override
  public CustomerResponse update(CustomerRequest request, String id) {
    Customer customerToUpdate = this.find(id);
    Customer customer = this.requestToEntity(request, customerToUpdate);
    return this.entityToResponse(this.customerRepository.save(customer));
  }

  @Override
  public void delete(String id) {
    Customer customer = this.find(id);
    this.customerRepository.delete(customer);
  }

  private CustomerResponse entityToResponse(Customer entity) {
    CustomerResponse response = new CustomerResponse();
    BeanUtils.copyProperties(entity, response);
    response.setBooking(entity.getBooking().stream().map(this::bookingToResponse).collect(Collectors.toList()));
    return response;
  }

  private BookingToCustomerResponse bookingToResponse(Booking entity) {
    BookingToCustomerResponse response = new BookingToCustomerResponse();

    BeanUtils.copyProperties(entity, response);
    response.setGuests(entity.getGuests().stream().map(this::guestToGuestToBooking).collect(Collectors.toList()));
    response.setRooms(entity.getRooms().stream().map(this::roomToRoomToBooking).collect(Collectors.toList()));
    return response;
  }

  // debe sarlir una lista en booking todas las reservas que tiene el cliente

  private Customer requestToEntity(CustomerRequest entity, Customer customer) {
    customer.setName(entity.getName());
    customer.setLastname(entity.getLastname());
    customer.setAge(entity.getAge());
    customer.setIdDocument(entity.getIdDocument());
    customer.setCellphone(entity.getCellphone());
    customer.setBooking(new ArrayList<>());

    return customer;
  }

  private Customer find(String id) {
    return this.customerRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Customer"));
  }

  private GuestToBookingResponse guestToGuestToBooking(Guest guest) {
    GuestToBookingResponse guestToBookingResponse = new GuestToBookingResponse();
    BeanUtils.copyProperties(guest, guestToBookingResponse);

    return guestToBookingResponse;
  }

  private RoomToBookingResponse roomToRoomToBooking(Room room) {
    RoomToBookingResponse roomToBookingResponse = new RoomToBookingResponse();
    BeanUtils.copyProperties(room, roomToBookingResponse);
    roomToBookingResponse.setRoomType(this.roomTypeToRoomTypeToAny(room.getRoomType()));
    roomToBookingResponse.setFloor(this.floorToFloorToAny(room.getFloor()));
    return roomToBookingResponse;
  }

  private FloorToAny floorToFloorToAny(Floor floor) {
    FloorToAny floorToAny = new FloorToAny();
    BeanUtils.copyProperties(floor, floorToAny);

    return floorToAny;
  }

  private RoomTypeToAnyResponse roomTypeToRoomTypeToAny(RoomType roomType) {
    RoomTypeToAnyResponse roomTypeToAnyResponse = new RoomTypeToAnyResponse();
    BeanUtils.copyProperties(roomType, roomTypeToAnyResponse);
    return roomTypeToAnyResponse;
  }

}
