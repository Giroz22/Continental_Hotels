package com.riwi.continental.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.continental.api.dto.request.HotelRequest;
import com.riwi.continental.api.dto.response.HotelResponse;
import com.riwi.continental.infrastructure.abstract_services.IHotelService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(path = "/hotels")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class HotelController {

    @Autowired
    private final IHotelService iHotelService;

    @GetMapping
    public ResponseEntity<Page<HotelResponse>> showHotels(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok(this.iHotelService.getAll(page - 1, size));
    }

    @PostMapping
    public ResponseEntity<HotelResponse> addHotel(@RequestBody HotelRequest hotelRequest) {
        return ResponseEntity.ok(this.iHotelService.create(hotelRequest));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<HotelResponse> filterById(@PathVariable String id) {
        return ResponseEntity.ok(this.iHotelService.findById(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<HotelResponse> updateHotel(@PathVariable String id, @RequestBody HotelRequest hotelRequest) {
        return ResponseEntity.ok(this.iHotelService.update(hotelRequest, id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String id) {
        this.iHotelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
