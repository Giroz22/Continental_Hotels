package com.riwi.continental.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.riwi.continental.api.dto.request.FloorRequest;
import com.riwi.continental.api.dto.request.HotelRequest;
import com.riwi.continental.api.dto.response.FloorToHotelResponse;
import com.riwi.continental.api.dto.response.HotelResponse;
import com.riwi.continental.domain.entities.Floor;
import com.riwi.continental.domain.entities.Hotel;
import com.riwi.continental.domain.repositories.HotelRepository;
import com.riwi.continental.infrastructure.abstract_services.IHotelService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HotelService implements IHotelService {

    @Autowired
    private final HotelRepository hotelRepository;

    @Override
    public Page<HotelResponse> getAll(int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public HotelResponse findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public HotelResponse create(HotelRequest hotelRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public HotelResponse update(HotelRequest hotelRequest, String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    private HotelResponse hotelToHotelResponse(Hotel hotel){
        HotelResponse hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(hotel, hotelResponse);
        //hotelResponse.getFloors().stream().map(floor -> this.floorToFloorToHotelResponse(floor).collect(Collectors.toList()));
        return hotelResponse;
    }
    private Hotel hotelRequestToHotel(HotelRequest hotelRequest,Hotel hotel){
        //hotel.setFloors(New ArrayList<>());
        BeanUtils.copyProperties(hotelRequest, hotel);
        return hotel;
    }
    private FloorToHotelResponse floorToFloorToHotelResponse(Floor floor){
        FloorToHotelResponse floorToHotelResponse = new FloorToHotelResponse();
        BeanUtils.copyProperties(floor, floorToHotelResponse);

        return floorToHotelResponse;
    }
}
