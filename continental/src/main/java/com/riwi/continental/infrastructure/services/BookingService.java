package com.riwi.continental.infrastructure.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.continental.api.dto.request.BookingRequest;
import com.riwi.continental.api.dto.response.BookingResponse;
import com.riwi.continental.api.dto.response.CustomerToBookingResponse;
import com.riwi.continental.api.dto.response.GuestToBookingResponse;
import com.riwi.continental.api.dto.response.RoomToBookingResponse;
import com.riwi.continental.domain.entities.Booking;
import com.riwi.continental.domain.entities.Customer;
import com.riwi.continental.domain.entities.Guest;
import com.riwi.continental.domain.entities.Room;
import com.riwi.continental.domain.repositories.BookingRepository;
import com.riwi.continental.domain.repositories.CustomerRepository;
import com.riwi.continental.domain.repositories.GuestRepository;
import com.riwi.continental.domain.repositories.RoomRepository;
import com.riwi.continental.infrastructure.abstract_services.IBookingService;
import com.riwi.continental.util.enums.StatusBooking;
import com.riwi.continental.util.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookingService  implements IBookingService{

    @Autowired
    private final BookingRepository bookingRepository ;

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final GuestRepository guestRepository;

    @Autowired
    private final RoomRepository roomRepository;

    @Override
    
    public Page<BookingResponse> getAll(int page, int size) {
        if(page < 0) page = 0;
        PageRequest pagination = PageRequest.of(page,size);

        return this.bookingRepository.findAll(pagination).map(this::entityToResponse);
    }

    @Override
    public BookingResponse findById(String id) {
        Booking booking = this.find(id);
        return this.entityToResponse(booking);
    }

    @Override
    public BookingResponse create(BookingRequest entity) {
        Customer customer = this.customerRepository.findById(entity.getCustomerId()).orElseThrow(() -> new IdNotFoundException("Customer"));
        Guest guest = this.guestRepository.findById(entity.getGuestId()).orElseThrow(() -> new IdNotFoundException("Guest"));
        Room room = this.roomRepository.findById(entity.getRoomId()).orElseThrow(() -> new IdNotFoundException("Room"));

        Booking booking = this.requestToEntity(entity, new Booking());
        if (entity.getDepartureDate().isBefore(entity.getAdmissionDate().plusDays(1))) {
            throw new IllegalArgumentException(" the booking must be atleast one day.");
        }
        booking.setPrice(new BigDecimal(0));
        booking.setStatus(StatusBooking.ACTIVE);
        booking.setCustomer(customer);
        booking.setGuests(new ArrayList<>());
        booking.setRooms(new ArrayList<>());

        return this.entityToResponse(this.bookingRepository.save(booking));

    }

    @Override
    public BookingResponse update(BookingRequest entity, String id) {
        Booking  bookingToUpdate = this.find(id);

        Booking booking = this.requestToEntity(entity, bookingToUpdate);

        return this.entityToResponse(this.bookingRepository.save(booking)); 
    }

    @Override
    public void delete(String id) {
       Booking booking = this.find(id);
       this.bookingRepository.delete(booking);
    }

    //Metodo para convertir una entidad en el dto de  respuesta de la entidad

    private BookingResponse entityToResponse(Booking entity){
        BookingResponse response = new BookingResponse();

        //Realizo la copia de entity a response
        BeanUtils.copyProperties(entity, response);

        response.setGuests(entity.getGuests().stream().map(this::guestToResponse).toList());

        response.setCustomer(this.costumerToResponse(entity.getCustomer()));

        response.setRooms(entity.getRooms().stream().map(this::roomToResponse).toList());

        return response;
    }

    private GuestToBookingResponse guestToResponse(Guest entity){
        GuestToBookingResponse response = new GuestToBookingResponse();

        BeanUtils.copyProperties(entity, response);

        return response;
    }

    private CustomerToBookingResponse costumerToResponse(Customer entity){
        CustomerToBookingResponse response = new CustomerToBookingResponse();

        BeanUtils.copyProperties(entity, response);
        return response;
    }

    private RoomToBookingResponse roomToResponse(Room entity){
        RoomToBookingResponse response = new RoomToBookingResponse();

        BeanUtils.copyProperties(entity, response);
        return response;
    } 

    private Booking requestToEntity(BookingRequest request, Booking booking){
        booking.setAdmissionDate(request.getAdmissionDate());
        booking.setDepartureDate(request.getDepartureDate());
        booking.setAdmissionTime(request.getAdmissionTime());
        booking.setDepartureTime(request.getDepartureTime());
        booking.setCustomer(this.customerRepository.findById(request.getCustomerId()).orElseThrow(()-> new IdNotFoundException("customer")));
        
        return booking;
    }

    private Booking find(String id){
        return this.bookingRepository.findById(id).orElseThrow(()-> new IdNotFoundException("Booking"));
    }

    // private List<GuestToBookingResponse> addGuestToBooking(String id){
        
    // }
    
}
