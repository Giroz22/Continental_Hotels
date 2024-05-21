package com.riwi.continental.infrastructure.services;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.continental.api.dto.request.RoomRequest;
import com.riwi.continental.api.dto.response.BookingToRoomsResponse;
import com.riwi.continental.api.dto.response.CustomerToBookingResponse;
import com.riwi.continental.api.dto.response.FloorToAny;
import com.riwi.continental.api.dto.response.GuestToBookingResponse;
import com.riwi.continental.api.dto.response.RoomResponse;
import com.riwi.continental.api.dto.response.RoomTypeToAnyResponse;
import com.riwi.continental.domain.entities.Booking;
import com.riwi.continental.domain.entities.Customer;
import com.riwi.continental.domain.entities.Floor;
import com.riwi.continental.domain.entities.Guest;
import com.riwi.continental.domain.entities.Room;
import com.riwi.continental.domain.entities.RoomType;
import com.riwi.continental.domain.repositories.FloorRepository;
import com.riwi.continental.domain.repositories.RoomRepository;
import com.riwi.continental.domain.repositories.RoomTypeRepository;
import com.riwi.continental.infrastructure.abstract_services.IRoomService;
import com.riwi.continental.util.exceptions.IdNotFoundException;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoomService implements IRoomService {
    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private final RoomTypeRepository typeRoomRepository;

    @Autowired
    private final FloorRepository floorRepository;

    @Override
    public Page<RoomResponse> getAll(int page, int size) {
        if (page < 0)
            page = 0;
        PageRequest pagination = PageRequest.of(page, size);
        return this.roomRepository.findAll(pagination).map(this::entityToResponse);
    }

    @Override
    public RoomResponse findById(String id) {
        return this.entityToResponse(this.find(id));
    }

    @Transactional
    @Override
    public RoomResponse create(RoomRequest entity) {
        RoomType typeRoom = this.typeRoomRepository.findById(entity.getRoomTypeId()).orElseThrow(() -> new IdNotFoundException("Room type"));
        Floor floor = this.floorRepository.findById(entity.getFloorId()).orElseThrow(() -> new IdNotFoundException("Floor"));
        
        Room room = this.requestToRoom(entity, new Room());

        room.setRoomType(typeRoom);
        room.setFloor(floor);
        room.setBookings(new ArrayList<>());

        return this.entityToResponse(this.roomRepository.save(room));
    }

    @Transactional
    @Override
    public RoomResponse update(RoomRequest request, String id) {
        Room room = this.find(id);
        RoomType typeRoom = this.typeRoomRepository.findById(request.getRoomTypeId()).orElseThrow(() -> new IdNotFoundException("Room Type"));
        Floor floor = this.floorRepository.findById(request.getFloorId()).orElseThrow(() -> new IdNotFoundException("Floor"));;

        room.setRoomType(typeRoom);
        room.setFloor(floor);
        room.setState(request.getState());
        this.requestToRoom(request, room);

        return this.entityToResponse(this.roomRepository.save(room));
    }

    @Override
    public void delete(String id) {
        Room room = this.find(id);
        this.roomRepository.delete(room);
    }

    private RoomResponse entityToResponse(Room entity) {
        RoomResponse roomResponse = new RoomResponse();
        RoomTypeToAnyResponse typeRoomDto = new RoomTypeToAnyResponse();
        FloorToAny floorDto = new FloorToAny();

        BeanUtils.copyProperties(entity, roomResponse);
        BeanUtils.copyProperties(entity.getRoomType(), typeRoomDto);
        BeanUtils.copyProperties(entity.getFloor(), floorDto);

        roomResponse.setRoomType(typeRoomDto);
        roomResponse.setFloor(floorDto);
        roomResponse.setBookings(entity.getBookings().stream().map(this::bookingToBookingToRoomsResponse).collect(Collectors.toList()));

        return roomResponse;
    }

    private Room requestToRoom(RoomRequest request, Room entity) {
        BeanUtils.copyProperties(request, entity);

        return entity;
    }

    private Room find(String id) {
        return this.roomRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Room"));
    }

    private BookingToRoomsResponse bookingToBookingToRoomsResponse(Booking booking) {

        BookingToRoomsResponse bookingToRoomsResponse = new BookingToRoomsResponse();
        BeanUtils.copyProperties(booking, bookingToRoomsResponse);

        bookingToRoomsResponse.setCustomer(this.customerToCustomerToBookingResponse(booking.getCustomer()));
        bookingToRoomsResponse.setGuests(
                booking.getGuests().stream().map(this::guestToGuestToBookingResponse).collect(Collectors.toList()));

        return bookingToRoomsResponse;
    }

    private GuestToBookingResponse guestToGuestToBookingResponse(Guest guest) {
        GuestToBookingResponse guestToBookingResponse = new GuestToBookingResponse();
        BeanUtils.copyProperties(guest, guestToBookingResponse);

        return guestToBookingResponse;
    }

    private CustomerToBookingResponse customerToCustomerToBookingResponse(Customer customer) {
        CustomerToBookingResponse customerToBookingResponse = new CustomerToBookingResponse();
        BeanUtils.copyProperties(customer, customerToBookingResponse);

        return customerToBookingResponse;
    }

}
