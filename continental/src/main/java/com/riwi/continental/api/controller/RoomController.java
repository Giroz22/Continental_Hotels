package com.riwi.continental.api.controller;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.continental.api.dto.request.RoomRequest;
import com.riwi.continental.api.dto.response.RoomResponse;
import com.riwi.continental.infrastructure.abstract_services.IRoomService;


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
@AllArgsConstructor
@RequestMapping("/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomController {
    @Autowired
    private final IRoomService roomService;

    
    @GetMapping
    public ResponseEntity<Page<RoomResponse>> getAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(this.roomService.getAll(page - 1, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(this.roomService.findById(id));
    }
    

    @PostMapping
    public ResponseEntity<RoomResponse> insert(@Validated @RequestBody RoomRequest room) {
        return ResponseEntity.ok(this.roomService.create(room));
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String id){
        Map<String, String> response = new HashMap<>();
        response.put("message", "Room succesfully eliminated");
        this.roomService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> update(@Validated @PathVariable String id, @RequestBody RoomRequest room) {
       return ResponseEntity.ok(this.roomService.update(room, id));
    }



}
