package com.riwi.continental.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.continental.api.dto.request.BookingRequest;
import com.riwi.continental.api.dto.response.BookingResponse;
import com.riwi.continental.domain.entities.Booking;
import com.riwi.continental.domain.repositories.BookingRepository;
import com.riwi.continental.util.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class BookingService  implements IBookingService{

    private final BookingRepository bookingRepository = null;

    @Override
    
    public Page<BookingResponse> getAll(int page, int size) {
        if(page < 0)
        page = 0;
        PageRequest pagination = PageRequest.of(page,size);

        return this.bookingRepository.findAll(pagination).map(booking -> this.entityToResponse(booking));
    }

    @Override
    public BookingResponse findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public BookingResponse create(BookingRequest entity) {

        Booking booking = this.requestToEntity(entity, new Booking());

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

        return response;
    }

    private Booking requestToEntity(BookingRequest entity, Booking booking){
        booking.setValor(entity.getValor());
        booking.setEstado(entity.getEstado());
        booking.setFecha_ingreso(entity.getFecha_ingreso());
        booking.setFecha_salida(entity.getFecha_salida());
        booking.setHora_entrada(entity.getHora_salida());
        booking.getHora_salida(entity.getHora_salida());

        return booking;
    }

    private Booking find(String id){
        return this.bookingRepository.findById(id).orElseThrow(()-> new IdNotFoundException("Booking"));
    }
    
}
