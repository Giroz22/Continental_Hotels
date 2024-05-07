package com.riwi.continental.infrastructure.services;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.continental.api.dto.request.FloorRequest;
import com.riwi.continental.api.dto.request.HotelRequest;
import com.riwi.continental.api.dto.response.FloorToHotelResponse;
import com.riwi.continental.api.dto.response.HotelResponse;
import com.riwi.continental.domain.entities.Floor;
import com.riwi.continental.domain.entities.Hotel;
import com.riwi.continental.domain.repositories.HotelRepository;
import com.riwi.continental.infrastructure.abstract_services.IHotelService;
import com.riwi.continental.util.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HotelService implements IHotelService {

    @Autowired
    private final HotelRepository hotelRepository;

    @Override
    public Page<HotelResponse> getAll(int page, int size) {
        if (page < 0) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page, size);

        return this.hotelRepository.findAll(pageable).map(hotel -> this.hotelToHotelResponse(hotel));

    }

    @Override
    public HotelResponse findById(String id) {
        Hotel hotel = this.findHotelById(id);

        return this.hotelToHotelResponse(hotel);
    }

    private Hotel findHotelById(String id) {
        return this.hotelRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Hotel"));
    }

    @Override
    public HotelResponse create(HotelRequest hotelRequest) {
        Hotel hotel = this.hotelRequestToHotel(hotelRequest, new Hotel());

        return this.hotelToHotelResponse(this.hotelRepository.save(hotel));
    }

    @Override
    public HotelResponse update(HotelRequest hotelRequest, String id) {
        Hotel hotel = this.findHotelById(id);
        hotel = this.hotelRequestToHotel(hotelRequest, hotel);

        return this.hotelToHotelResponse(this.hotelRepository.save(hotel));
    }

    @Override
    public void delete(String id) {
        Hotel hotel = this.findHotelById(id);

        this.hotelRepository.delete(hotel);
    }

    private HotelResponse hotelToHotelResponse(Hotel hotel) {
        HotelResponse hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(hotel, hotelResponse);
        hotelResponse.setFloors(
                hotel.getFloors().stream().map(floor -> this.floorToFloorToHotelResponse(floor))
                        .collect(Collectors.toList()));
        return hotelResponse;
    }

    private Hotel hotelRequestToHotel(HotelRequest hotelRequest, Hotel hotel) {
        hotel.setFloors(new ArrayList<>());
        BeanUtils.copyProperties(hotelRequest, hotel);
        return hotel;
    }

    private FloorToHotelResponse floorToFloorToHotelResponse(Floor floor) {
        FloorToHotelResponse floorToHotelResponse = new FloorToHotelResponse();
        BeanUtils.copyProperties(floor, floorToHotelResponse);

        return floorToHotelResponse;
    }
}
