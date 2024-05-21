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
import com.riwi.continental.api.dto.response.FloorResponse;
import com.riwi.continental.api.dto.response.HotelToFloorResponse;
import com.riwi.continental.api.dto.response.RoomToAny;
import com.riwi.continental.api.dto.response.RoomTypeToAnyResponse;
import com.riwi.continental.domain.entities.Floor;
import com.riwi.continental.domain.entities.Hotel;
import com.riwi.continental.domain.entities.Room;
import com.riwi.continental.domain.entities.RoomType;
import com.riwi.continental.domain.repositories.FloorRepository;
import com.riwi.continental.domain.repositories.HotelRepository;
import com.riwi.continental.infrastructure.abstract_services.IFloorService;
import com.riwi.continental.util.enums.StatusFloor;
import com.riwi.continental.util.exceptions.IdNotFoundException;

import jakarta.transaction.Transactional;
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

        return this.floorRepository.findAll(pageable).map(this::floorToFloorResponse);
    }

    @Override
    public FloorResponse findById(String id) {
        Floor floor = this.findFloorById(id);

        return this.floorToFloorResponse(floor);
    }

    private Floor findFloorById(String id) {
        return this.floorRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Floor"));
    }

    @Transactional
    @Override
    public FloorResponse create(FloorRequest floorRequest) {
        Hotel hotel = this.hotelRepository.findById(floorRequest.getHotelId())
                .orElseThrow(() -> new IdNotFoundException("Hotel"));
        Floor floor = this.floorRequestToFloor(floorRequest, new Floor());
        floor.setHotel(hotel);

        return this.floorToFloorResponse(this.floorRepository.save(floor));
    }

    @Transactional
    @Override
    public FloorResponse update(FloorRequest floorRequest, String id) {
        Floor floor = this.findFloorById(id);

        this.floorRequestToFloor(floorRequest, floor);
        Hotel hotel = this.hotelRepository.findById(floorRequest.getHotelId())
                .orElseThrow(() -> new IdNotFoundException("Hotel"));
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

        floorResponse.setRooms(floor.getRooms().stream().map(this::roomToRoomToAny).collect(Collectors.toList()));

        return floorResponse;
    }

    private Floor floorRequestToFloor(FloorRequest floorRequest, Floor floor) {
        BeanUtils.copyProperties(floorRequest, floor);
        floor.setStatusFloor(StatusFloor.AVAILABLE);
        floor.setRooms(new ArrayList<>());
        return floor;
    }

    private RoomToAny roomToRoomToAny(Room room) {
        RoomToAny roomToAny = new RoomToAny();
        BeanUtils.copyProperties(room, roomToAny);
        roomToAny.setRoomTypeToAnyResponse(this.roomTypeToRoomTypeToAnyResponse(room.getRoomType()));

        return roomToAny;
    }

    private RoomTypeToAnyResponse roomTypeToRoomTypeToAnyResponse(RoomType roomType) {
        RoomTypeToAnyResponse roomTypeToAnyResponse = new RoomTypeToAnyResponse();
        BeanUtils.copyProperties(roomType, roomTypeToAnyResponse);

        return roomTypeToAnyResponse;
    }
}
