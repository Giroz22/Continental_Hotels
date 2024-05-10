package com.riwi.continental.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.continental.api.dto.request.RoomRequest;
import com.riwi.continental.api.dto.response.FloorToHotelResponse;
import com.riwi.continental.api.dto.response.RoomResponse;
import com.riwi.continental.api.dto.response.RoomTypeToAnyResponse;
import com.riwi.continental.domain.entities.Floor;
import com.riwi.continental.domain.entities.Room;
import com.riwi.continental.domain.entities.RoomType;
import com.riwi.continental.domain.repositories.FloorRepository;
import com.riwi.continental.domain.repositories.RoomRepository;
import com.riwi.continental.domain.repositories.RoomTypeRepository;
import com.riwi.continental.infrastructure.abstract_services.IRoomService;
import com.riwi.continental.util.enums.StateRoom;
import com.riwi.continental.util.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoomService implements IRoomService{
    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private final RoomTypeRepository typeRoomRepository;

    @Autowired
    private final FloorRepository floorRepository;

    @Override
    public Page<RoomResponse> getAll(int page, int size) {
        if (page < 0)  page = 0;
        PageRequest pagination = PageRequest.of(page, size);
        return this.roomRepository.findAll(pagination).map(this::entityToResponse);
    }

    @Override
    public RoomResponse findById(String id) {
        return this.entityToResponse(this.find(id));
    }

    @Override
    public RoomResponse create(RoomRequest entity) {
       RoomType typeRoom = this.typeRoomRepository.findById(entity.getRoomTypeId()).orElseThrow(() -> new IdNotFoundException("room"));
       Floor floor = this.floorRepository.findById(entity.getFloorId()).orElseThrow(() -> new IdNotFoundException("room"));

        Room room = this.requestToRoom(entity, new Room());
        

        room.setRoomType(typeRoom);
        room.setFloor(floor);


        return this.entityToResponse(this.roomRepository.save(room));
    }

    @Override
    public RoomResponse update(RoomRequest request, String id) {
        Room room = this.find(id);
        RoomType typeRoom = this.typeRoomRepository.findById(request.getRoomTypeId()).orElseThrow(() -> new IdNotFoundException("room"));

       Floor floor = this.floorRepository.findById(request.getFloorId()).orElseThrow(() -> new IdNotFoundException("room"));

        room.setRoomType(typeRoom);
        room.setFloor(floor);
        room.setState(request.getState());
        room = this.requestToRoom(request, room);
        

        return this.entityToResponse(this.roomRepository.save(room));
    }

    @Override
    public void delete(String id) {
        Room room = this.find(id);
        this.roomRepository.delete(room);
    }


    private RoomResponse entityToResponse(Room entity){
        RoomResponse roomResponse = new RoomResponse();
        RoomTypeToAnyResponse typeRoomDto = new RoomTypeToAnyResponse();
        FloorToHotelResponse floorDto = new FloorToHotelResponse();

        
        BeanUtils.copyProperties(entity, roomResponse);
        BeanUtils.copyProperties(entity.getRoomType(), typeRoomDto);
        BeanUtils.copyProperties(entity.getFloor(), floorDto);


        roomResponse.setRoomType(typeRoomDto);
        roomResponse.setFloor(floorDto);

        return roomResponse;
    }

    private Room requestToRoom(RoomRequest request, Room entity){
        BeanUtils.copyProperties(request, entity);
       
        return entity;
    }
 
    private Room find(String id){
        return this.roomRepository.findById(id).orElseThrow(() -> new IdNotFoundException("room"));
    }



}
