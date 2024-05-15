package com.riwi.continental.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.continental.api.dto.request.BookingRequest;
import com.riwi.continental.api.dto.response.BookingResponse;
import com.riwi.continental.domain.entities.Booking;
import com.riwi.continental.infrastructure.abstract_services.IBookingService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/booking")
@AllArgsConstructor
public class BookingController {

    @Autowired
    private final IBookingService bookingService;

    

    @GetMapping
    public ResponseEntity<Page<BookingResponse>> getAll(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "3") int size
    )
    {
        return ResponseEntity.ok(this.bookingService.getAll(page-1, size));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BookingResponse> get(
        @PathVariable String id
    ){
        return ResponseEntity.ok(this.bookingService.findById(id));
    }

    
    @PostMapping
    public ResponseEntity<BookingResponse> insert(
        @Validated @RequestBody BookingRequest booking)
        {
            this.bookingService.create(booking);
            return ResponseEntity.ok(this.bookingService.create(booking));
        }

    @PutMapping(path = "/{id}")
    public ResponseEntity <BookingResponse> update(
        @PathVariable String id,
        @Validated @RequestBody BookingRequest booking){
            return ResponseEntity.ok(this.bookingService.update(booking, id));
        }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        this.bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<Booking>> getOutsideOrders(@PathVariable String id) {
        return ResponseEntity.ok(this.bookingService.getCustomerBooking(id));
    }

    

}
