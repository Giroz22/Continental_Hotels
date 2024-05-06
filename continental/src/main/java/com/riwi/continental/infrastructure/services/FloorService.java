package com.riwi.continental.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.riwi.continental.api.dto.request.FloorRequest;
import com.riwi.continental.api.dto.response.FloorResponse;
import com.riwi.continental.api.dto.response.HotelToFloorResponse;
import com.riwi.continental.domain.entities.Floor;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public FloorResponse findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public FloorResponse create(FloorRequest entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public FloorResponse update(FloorRequest entity, String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    private FloorResponse floorToFloorResponse(Floor floor){
        FloorResponse floorResponse = new FloorResponse();

        //HotelToFloorResponse hotelToFloorResponse = new HotelToFloorResponse();
        //BeanUtils.copyProperties(floor.getHotel(), hotelToFloorResponse);
        BeanUtils.copyProperties(floor, floorResponse);

        return floorResponse;
    }
    private Floor floorRequestToFloor(FloorRequest floorRequest, Floor floor){
        BeanUtils.copyProperties(floorRequest, floor);
        floor.setStatusFloor(StatusFloor.AVAILABLE);
        return floor;
    }
}
