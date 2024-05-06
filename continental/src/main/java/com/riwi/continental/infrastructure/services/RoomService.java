package com.riwi.continental.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.continental.api.dto.request.RoomRequest;
import com.riwi.continental.api.dto.response.RoomResponse;
import com.riwi.continental.api.dto.response.RoomTypeToRoomResponse;
import com.riwi.continental.domain.entities.Room;
import com.riwi.continental.domain.entities.RoomType;
import com.riwi.continental.domain.repositories.RoomRepository;
import com.riwi.continental.domain.repositories.RoomTypeRepository;
import com.riwi.continental.infrastructure.abstract_services.IRoomService;
import com.riwi.continental.util.enums.StateRoom;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoomService implements IRoomService{
    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private final RoomTypeRepository typeRoomRepository;

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
       RoomType typeRoom = this.typeRoomRepository.findById(entity.getRoomType_id()).orElseThrow();

        Room room = this.requestToRoom(entity, new Room());
        room.setRoomType(typeRoom);

        return this.entityToResponse(this.roomRepository.save(room));
    }

    @Override
    public RoomResponse update(RoomRequest entity, String id) {
        Room room = this.find(id);

        RoomType typeRoom = this.typeRoomRepository.findById(entity.getRoomType_id()).orElseThrow();

        room = this.requestToRoom(entity, room);
        room.setRoomType(typeRoom);
        room.setState(entity.getStatus());

        return this.entityToResponse(this.roomRepository.save(room));
    }

    @Override
    public void delete(String id) {
        Room room = this.find(id);
        this.roomRepository.delete(room);
    }


    private RoomResponse entityToResponse(Room entity){
        RoomResponse response = new RoomResponse();

        BeanUtils.copyProperties(entity, response);
        RoomTypeToRoomResponse typeRoomDto = new RoomTypeToRoomResponse();

        BeanUtils.copyProperties(entity.getRoomType(), typeRoomDto);

        response.setRoomType(typeRoomDto);
        return response;
    }

    private Room requestToRoom(RoomRequest request, Room entity){
        BeanUtils.copyProperties(request, entity);
        entity.setState(StateRoom.AVAILABLE);
        return entity;
    }

    private Room find(String id){
        return this.roomRepository.findById(id).orElseThrow();
    }



}
