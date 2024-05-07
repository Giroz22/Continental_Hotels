package com.riwi.continental.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.continental.api.dto.request.FloorRequest;
import com.riwi.continental.api.dto.response.FloorResponse;
import com.riwi.continental.api.dto.response.HotelToFloorResponse;
import com.riwi.continental.domain.entities.Floor;
import com.riwi.continental.domain.entities.Hotel;
import com.riwi.continental.domain.repositories.FloorRepository;
import com.riwi.continental.domain.repositories.HotelRepository;
import com.riwi.continental.infrastructure.abstract_services.IFloorService;
import com.riwi.continental.util.enums.StatusFloor;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FloorService implements IFloorService {

    @Autowired
    private final FloorRepository floorRepository;
    @Autowired
    private final HotelRepository hotelRepository;

    @Override
    public Page<FloorResponse> getAll(int page, int size) {
        if (page < 0) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page, size);

        return this.floorRepository.findAll(pageable).map(floor -> this.floorToFloorResponse(floor));
    }

    @Override
    public FloorResponse findById(String id) {
        Floor floor = this.findFloorById(id);

        return this.floorToFloorResponse(floor);
    }

    private Floor findFloorById(String id) {
        return this.floorRepository.findById(id).orElseThrow();
    }

    @Override
    public FloorResponse create(FloorRequest floorRequest) {
        Hotel hotel = this.hotelRepository.findById(floorRequest.getHotelId()).orElseThrow();
        Floor floor = this.floorRequestToFloor(floorRequest, new Floor());
        floor.setHotel(hotel);

        return this.floorToFloorResponse(this.floorRepository.save(floor));
    }

    @Override
    public FloorResponse update(FloorRequest floorRequest, String id) {
        Floor floor = this.findFloorById(id);

        floor = this.floorRequestToFloor(floorRequest, floor);
        Hotel hotel = this.hotelRepository.findById(floorRequest.getHotelId()).orElseThrow();
        floor.setHotel(hotel);

        return this.floorToFloorResponse(this.floorRepository.save(floor));
    }

    @Override
    public void delete(String id) {
        Floor floor = this.findFloorById(id);

        this.floorRepository.delete(floor);
    }

    private FloorResponse floorToFloorResponse(Floor floor) {
        FloorResponse floorResponse = new FloorResponse();

        HotelToFloorResponse hotelToFloorResponse = new HotelToFloorResponse();
        BeanUtils.copyProperties(floor.getHotel(), hotelToFloorResponse);
        BeanUtils.copyProperties(floor, floorResponse);

        floorResponse.setHotelToFloorResponse(hotelToFloorResponse);

        return floorResponse;
    }

    private Floor floorRequestToFloor(FloorRequest floorRequest, Floor floor) {
        BeanUtils.copyProperties(floorRequest, floor);
        floor.setStatusFloor(StatusFloor.AVAILABLE);
        return floor;
    }
}
