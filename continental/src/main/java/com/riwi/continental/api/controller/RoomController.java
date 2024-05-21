package com.riwi.continental.api.controller;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.continental.api.dto.errors.ErrorResponse;
import com.riwi.continental.api.dto.errors.ErrorsResponse;
import com.riwi.continental.api.dto.request.RoomRequest;
import com.riwi.continental.api.dto.response.RoomResponse;
import com.riwi.continental.infrastructure.abstract_services.IRoomService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

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
public class RoomController {
    @Autowired
    private final IRoomService roomService;
    
    @Operation(summary = "this method allows get all the list of rooms in paginated form")
    @ApiResponse(responseCode = "400", description = "When the connection with the data base fail", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))})
    @GetMapping
    public ResponseEntity<Page<RoomResponse>> getAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(this.roomService.getAll(page - 1, size));
    }
    


    @Operation(summary = "this method allows get a room find with a id")
    @ApiResponse(responseCode = "400", description = "When the id is not valid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(this.roomService.findById(id));
    }
    

    @Operation(summary = "This method allows create a room with the dates sent")
    @ApiResponse(responseCode = "400", description = "When there is an error in the date sent to the datebase", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))})
    @PostMapping
    public ResponseEntity<RoomResponse> insert(@Validated @RequestBody RoomRequest room) {
        return ResponseEntity.ok(this.roomService.create(room));
    }
    

    @Operation(summary = "This method allows you delete a room for a id especific")
    @ApiResponse(responseCode = "400", description = "When the id it's not valid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String id){
        Map<String, String> response = new HashMap<>();
        response.put("message", "Room succesfully eliminated");
        this.roomService.delete(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "This method allows you modify a room for a id especific")
    @ApiResponse(responseCode = "400", description = "When the id it's not valid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> update(@Validated @PathVariable String id, @RequestBody RoomRequest room) {
       return ResponseEntity.ok(this.roomService.update(room, id));
    }



}
