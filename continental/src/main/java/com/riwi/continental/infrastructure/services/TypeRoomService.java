package com.riwi.continental.infrastructure.services;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.continental.api.dto.request.RoomTypeRequest;
import com.riwi.continental.api.dto.response.RoomToAny;
import com.riwi.continental.api.dto.response.RoomTypeResponse;
import com.riwi.continental.domain.entities.Room;
import com.riwi.continental.domain.entities.RoomType;
import com.riwi.continental.domain.repositories.RoomTypeRepository;
import com.riwi.continental.infrastructure.abstract_services.IRoomTypeService;
import com.riwi.continental.util.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TypeRoomService implements IRoomTypeService {
    
    @Autowired
    private final RoomTypeRepository typeRoomRepository;

    @Override
    public Page<RoomTypeResponse> getAll(int page, int size) {
        if (page< 0) page = 0;
        PageRequest pagination = PageRequest.of(page, size);
        return this.typeRoomRepository.findAll(pagination).map(this::entityToResponse);
    }

    @Override
    public RoomTypeResponse findById(String id) {
        RoomType typeRoom = this.find(id);
        return this.entityToResponse(typeRoom);
    }

    @Override
    public RoomTypeResponse create(RoomTypeRequest request) {
       RoomType typeRoom = this.requestToEntity(request, new RoomType());
       return this.entityToResponse(this.typeRoomRepository.save(typeRoom));
    }

    @Override
    public RoomTypeResponse update(RoomTypeRequest request, String id) {
        RoomType typeRoomToUpdate = this.find(id);
        RoomType typeRoom = this.requestToEntity(request, typeRoomToUpdate);
        return this.entityToResponse(this.typeRoomRepository.save(typeRoom));
    }

    @Override
    public void delete(String id) {
        RoomType typeRoom = this.find(id);
        this.typeRoomRepository.delete(typeRoom);
    }


    private RoomTypeResponse entityToResponse(RoomType entity){
        RoomTypeResponse response = new RoomTypeResponse();
        BeanUtils.copyProperties(entity, response);

        response.setRooms(entity.getRooms().stream().map(this::roomToTypeRoom).collect(Collectors.toList()));
        return response;

    }

    private RoomToAny roomToTypeRoom(Room entity){
        RoomToAny response = new RoomToAny();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    private RoomType requestToEntity(RoomTypeRequest request,RoomType typeRoom){
        BeanUtils.copyProperties(request, typeRoom);
        typeRoom.setRooms(new ArrayList<>());
        return typeRoom;
    }

    private RoomType find(String id){
        return this.typeRoomRepository.findById(id).orElseThrow(() -> new IdNotFoundException("roomType"));
    }


}
