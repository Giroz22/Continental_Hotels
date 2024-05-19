package com.riwi.continental.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.continental.api.dto.request.RoomTypeRequest;
import com.riwi.continental.api.dto.response.RoomTypeResponse;
import com.riwi.continental.infrastructure.abstract_services.IRoomTypeService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@AllArgsConstructor
@RequestMapping("/rooms-type")
@CrossOrigin(origins = "http://localhost:5173")
public class TypeRoomController {
    @Autowired
    private final IRoomTypeService typeRoomService;

    @GetMapping
    public ResponseEntity<Page<RoomTypeResponse>> getAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size){
        return ResponseEntity.ok(this.typeRoomService.getAll(page - 1, size));
    }


    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeResponse> getById(@PathVariable String id) {
       return ResponseEntity.ok(this.typeRoomService.findById(id));
    }


    @PostMapping
    public ResponseEntity<RoomTypeResponse> insert(@Validated @RequestBody RoomTypeRequest typeRoom) {
       return ResponseEntity.ok(this.typeRoomService.create(typeRoom));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String id){
        Map<String, String> response = new HashMap<>();
        response.put("message", "Type of room correctly eliminated");
        this.typeRoomService.delete(id);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RoomTypeResponse> update(@Validated @PathVariable String id, @RequestBody RoomTypeRequest typeRoom) {
        return ResponseEntity.ok(this.typeRoomService.update(typeRoom, id));
    }
    
}
