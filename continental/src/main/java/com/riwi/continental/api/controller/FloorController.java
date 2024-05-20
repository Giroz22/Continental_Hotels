package com.riwi.continental.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.continental.api.dto.request.FloorRequest;
import com.riwi.continental.api.dto.response.FloorResponse;
import com.riwi.continental.infrastructure.abstract_services.IFloorService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@CrossOrigin(origins = "http://localhost:3000/api")
@RequestMapping(path = "/floors")
@AllArgsConstructor
public class FloorController {

    @Autowired
    private final IFloorService iFloorService;

    @GetMapping
    public ResponseEntity<Page<FloorResponse>> showFloors(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok(this.iFloorService.getAll(page - 1, size));
    }

    @PostMapping(path = "/add")
    public ResponseEntity<FloorResponse> addFloor(@Validated @RequestBody FloorRequest floorRequest) {

        return ResponseEntity.ok(this.iFloorService.create(floorRequest));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<FloorResponse> filterById(@PathVariable String id) {
        return ResponseEntity.ok(this.iFloorService.findById(id));
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<FloorResponse> updateFloor(@PathVariable String id,
            @Validated @RequestBody FloorRequest floorRequest) {

        return ResponseEntity.ok(this.iFloorService.update(floorRequest, id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteFloor(@PathVariable String id) {
        this.iFloorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
