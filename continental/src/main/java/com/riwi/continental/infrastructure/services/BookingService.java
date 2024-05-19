package com.riwi.continental.infrastructure.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.continental.api.dto.request.BookingRequest;
import com.riwi.continental.api.dto.request.GuestToBookingRequest;
import com.riwi.continental.api.dto.response.BookingResponse;
import com.riwi.continental.api.dto.response.CustomerToBookingResponse;
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
import com.riwi.continental.domain.repositories.BookingRepository;
import com.riwi.continental.domain.repositories.CustomerRepository;
import com.riwi.continental.domain.repositories.GuestRepository;
import com.riwi.continental.domain.repositories.RoomRepository;
import com.riwi.continental.infrastructure.abstract_services.IBookingService;
import com.riwi.continental.util.enums.StatusBooking;
import com.riwi.continental.util.exceptions.BookingException;
import com.riwi.continental.util.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookingService implements IBookingService {

    @Autowired
    private final BookingRepository bookingRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final GuestRepository guestRepository;

    @Autowired
    private final RoomRepository roomRepository;

    @Override
    public Page<BookingResponse> getAll(int page, int size) {
        if (page < 0)
            page = 0;
        PageRequest pagination = PageRequest.of(page, size);

        return this.bookingRepository.findAll(pagination).map(this::entityToResponse);
    }

    @Override
    public BookingResponse findById(String id) {
        Booking booking = this.find(id);
        return this.entityToResponse(booking);
    }

    @Override
    public BookingResponse create(BookingRequest request) {      
        Booking booking = this.requestToEntity(request, new Booking());
        
        booking.setStatus(StatusBooking.ACTIVE);

        return this.entityToResponse(this.bookingRepository.save(booking));
    }

    @Override
    public BookingResponse update(BookingRequest request, String id) {
        Booking bookingToUpdate = this.find(id);

        Booking booking = this.requestToEntity(request, bookingToUpdate);

        booking.setId(bookingToUpdate.getId());
        booking.setStatus(bookingToUpdate.getStatus());
        booking.setGuests(bookingToUpdate.getGuests());

        return this.entityToResponse(this.bookingRepository.save(booking));
    }

    @Override
    public void delete(String id) {
        Booking booking = this.find(id);
        this.bookingRepository.delete(booking);
    }

    //Domain logic
    private void validateDateOfRooms(String idBooking, List<Room> listRooms, LocalDate admissionDateNewBooking, LocalDate departureDateNewBooking){
        for (Room room : listRooms) {
            for(Booking bookingActive : room.getBookings()){
                if(bookingActive.getStatus() != StatusBooking.ACTIVE) return;

                LocalDate admissionDateCurrentBooking = bookingActive.getAdmissionDate();
                LocalDate departureDateCurrentBooking =  bookingActive.getDepartureDate();

                boolean isRoomAvailable = !(departureDateNewBooking.isBefore(admissionDateCurrentBooking) || 
                admissionDateNewBooking.isAfter(departureDateCurrentBooking));

                boolean isSameBokking = bookingActive.getId() == idBooking; 
                
                if (isRoomAvailable){ 
                    System.out.println("No disponible");
                    if(!isSameBokking){
                        System.out.println("Es otra reserva");
                        throw new BookingException(String.format("The room %s is already booked", room.getRoomNum()));
                    }
                }                            
            }
        }
    }

    private void validateMinNumDaysBooking(LocalDate admissionDate, LocalDate departureDate){
        int minDays = 1;

        if (departureDate.isBefore(admissionDate.plusDays(minDays))) {
            throw new BookingException("The booking must be at least one day.");
        }
    }

    private BigDecimal calculatePriceBooking(List<Room> rooms, LocalDate admissionDate, LocalDate departureDate){
        BigDecimal priceBooking = BigDecimal.ZERO;
        
        long numDays = ChronoUnit.DAYS.between(admissionDate, departureDate);

        for(Room room:rooms){
            priceBooking = priceBooking.add(room.getPrice());
        }

        priceBooking = priceBooking.multiply(new BigDecimal(numDays));
    
        return priceBooking;
    }

    // Metodo para convertir una entidad en el dto de respuesta de la entidad
    private Booking requestToEntity(BookingRequest request, Booking booking) {        
        Customer customer = this.customerRepository.findById(request.getCustomerId()).orElseThrow(
            () -> new IdNotFoundException("customer")
        );

        List<Room> listRooms = 
        request.getListRoomId().stream().map(
            (String idRoom) -> this.roomRepository.findById(idRoom).orElseThrow(
                () -> new IdNotFoundException("room")
            )
        ).toList();

        BigDecimal price = calculatePriceBooking(listRooms, request.getAdmissionDate(), request.getDepartureDate());

        //Validations
        validateMinNumDaysBooking(request.getAdmissionDate(), request.getDepartureDate());
        validateDateOfRooms(booking.getId(),listRooms, request.getAdmissionDate(), request.getDepartureDate());

        return Booking.builder()
        .admissionDate(request.getAdmissionDate())
        .departureDate(request.getDepartureDate())
        .admissionTime(request.getAdmissionTime())
        .departureTime(request.getDepartureTime())
        .customer(customer)
        .rooms(listRooms)
        .guests(new ArrayList<Guest>())
        .price(price)
        .build();

    }


    private BookingResponse entityToResponse(Booking entity) {
        BookingResponse response = new BookingResponse();
        
        BeanUtils.copyProperties(entity, response);

        response.setGuests(entity.getGuests().stream().map(this::guestToResponse).toList());

        response.setCustomer(this.costumerToResponse(entity.getCustomer()));

        response.setRooms(entity.getRooms().stream().map(this::roomToResponse).toList());

        return response;
    }


    private GuestToBookingResponse guestToResponse(Guest entity) {
        GuestToBookingResponse response = new GuestToBookingResponse();

        BeanUtils.copyProperties(entity, response);

        return response;
    }

    private CustomerToBookingResponse costumerToResponse(Customer entity) {
        CustomerToBookingResponse response = new CustomerToBookingResponse();

        BeanUtils.copyProperties(entity, response);
        return response;
    }

    private RoomToBookingResponse roomToResponse(Room entity) {
        RoomToBookingResponse response = new RoomToBookingResponse();

        BeanUtils.copyProperties(entity, response);
        response.setRoomType(this.roomTypeToRoomTypeToAny(entity.getRoomType()));
        response.setFloor(this.floorToFloorToAny(entity.getFloor()));
        return response;
    }

    private Booking find(String id) {
        return this.bookingRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Booking"));
    }

    private RoomTypeToAnyResponse roomTypeToRoomTypeToAny(RoomType roomType) {
        RoomTypeToAnyResponse roomTypeToAnyResponse = new RoomTypeToAnyResponse();
        BeanUtils.copyProperties(roomType, roomTypeToAnyResponse);

        return roomTypeToAnyResponse;
    }

    private FloorToAny floorToFloorToAny(Floor floor) {
        FloorToAny floorToAny = new FloorToAny();
        BeanUtils.copyProperties(floor, floorToAny);

        return floorToAny;
    }
}
