package com.riwi.continental.infrastructure.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
import com.riwi.continental.domain.entities.Hotel;
import com.riwi.continental.domain.entities.Room;
import com.riwi.continental.domain.entities.RoomType;
import com.riwi.continental.domain.repositories.BookingRepository;
import com.riwi.continental.domain.repositories.CustomerRepository;
import com.riwi.continental.domain.repositories.HotelRepository;
import com.riwi.continental.domain.repositories.RoomRepository;
import com.riwi.continental.infrastructure.abstract_services.IBookingService;
import com.riwi.continental.util.enums.AgeCategory;
import com.riwi.continental.util.enums.StateRoom;
import com.riwi.continental.util.enums.StatusBooking;
import com.riwi.continental.util.exceptions.BookingException;
import com.riwi.continental.util.exceptions.IdNotFoundException;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookingService implements IBookingService {

    @Autowired
    private final BookingRepository bookingRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private final HotelRepository hotelRepository;

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

    @Transactional
    @Override
    public BookingResponse create(BookingRequest request) {      
        Booking booking = this.requestToEntity(request, new Booking());
        
        booking.setStatus(StatusBooking.ACTIVE);

        return this.entityToResponse(this.bookingRepository.save(booking));
    }

    @Transactional
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

    @Override
    public BookingResponse checkIn(String idBooking, List<GuestToBookingRequest> listGuestRequest){
        Booking booking = this.find(idBooking);

        //Realize validations
        this.validateCheckIn(booking);

        //Change reservation status
        booking.setStatus(StatusBooking.INPROCESS);

        //Assigns the current time
        booking.setAdmissionTime(LocalTime.now());

        //Changes the status of all booked rooms
        List<Room> listRooms = booking.getRooms();

        for (Room room : listRooms) {
            room.setState(StateRoom.RESERVED);
            roomRepository.save(room);
        }

        //Adds all guests to the reservation
        List<Guest> listGuest = listGuestRequest.stream().map(
            (GuestToBookingRequest guestRequest) -> this.requestToGuest(idBooking, guestRequest)
        ).toList();

        List<Guest> newGuestList = new ArrayList<>();
        newGuestList.addAll(listGuest);
        booking.setGuests(newGuestList);

        //We keep the booking updated
        Booking bookingUpdated = this.bookingRepository.save(booking);

        return entityToResponse(bookingUpdated);
    }

    private void validateCheckIn(Booking booking){
        String msjError = null;

        //We validate if you have already check-in.
        boolean isBookinActive =  booking.getStatus() == StatusBooking.ACTIVE;

        if (!isBookinActive) {
            msjError = (String.format("checking cannot be done again"));
        }

        //Validate if the current date is the same as the booking date 
        boolean isCheckinBefore = LocalDate.now().isBefore(booking.getAdmissionDate());
        boolean isCheckinAfter = LocalDate.now().isAfter(booking.getAdmissionDate());

        if (isCheckinBefore) {
            msjError = (String.format("Check-in is not yet possible, the reservation is scheduled for %s", booking.getAdmissionDate()));
        }

        if (isCheckinAfter) {
            msjError = (String.format("Check-in date expired on %s", booking.getAdmissionDate()));
        }

        //Validate if an error occurred
        if (msjError != null) {
            throw new BookingException(msjError);
        }
    }

    @Override
    public BookingResponse checkOut(String id, List<GuestToBookingRequest> listGuestRequest){
        Booking booking = this.find(id);

        //Validations
        this.validateCheckOut(booking);

        //Change reservation status
        booking.setStatus(StatusBooking.FINISHED);

        //Assigns the current time
        booking.setDepartureTime(LocalTime.now());

        //Changes the status of all booked rooms
        List<Room> listRooms = booking.getRooms();

        for (Room room : listRooms) {
            room.setState(StateRoom.AVAILABLE);
            roomRepository.save(room);
        }

        // Let's add the profits to the hotel
        Hotel hotel = booking.getRooms().get(0).getFloor().getHotel();
        hotel.setEarnings(hotel.getEarnings() + booking.getPrice().floatValue());
        this.hotelRepository.save(hotel);
        
        return entityToResponse(booking);
    }

    private void validateCheckOut(Booking booking){
        String msjError = null;

        //Validate if the booking is in process 
        boolean isBookinInProcess =  booking.getStatus() == StatusBooking.INPROCESS;

        if (!isBookinInProcess) {
            msjError = (String.format("check-out cannot be done again"));
        }

        //Validate if the current date is the same as the booking date 
        boolean isCheckoutBefore = booking.getDepartureDate().isBefore(booking.getAdmissionDate());

        boolean isCheckoutAfter = LocalDate.now().isAfter(booking.getDepartureDate());

        if (isCheckoutBefore) {
            msjError = ("It is not possible to checkout before checkin");
        }

        //If the checkout is after the booked date the booking price increases
        if (isCheckoutAfter) {
            booking.setPrice(booking.getPrice().add(calculatePriceBooking(booking.getRooms(), booking.getDepartureDate(),LocalDate.now())));
        }

        //Validate if an error occurred
        if (msjError != null) {
            throw new BookingException(msjError);
        }
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
                    if(!isSameBokking){                        
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

    // Methods to convert an entity to the entity's response data

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

    private Guest requestToGuest(String idBooking, GuestToBookingRequest request) {
        return Guest.builder()
        .idDocument(request.getIdDocument())
        .name(request.getName())
        .lastname(request.getLastname())
        .age(request.getAge())
        .ageCategory(AgeCategory.ADULT)
        .booking(this.find(idBooking))
        .build();
    }
}
