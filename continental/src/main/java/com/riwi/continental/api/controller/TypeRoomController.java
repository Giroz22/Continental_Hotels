package com.riwi.continental.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.continental.api.dto.errors.ErrorResponse;
import com.riwi.continental.api.dto.errors.ErrorsResponse;
import com.riwi.continental.api.dto.request.RoomTypeRequest;
import com.riwi.continental.api.dto.response.RoomTypeResponse;
import com.riwi.continental.infrastructure.abstract_services.IRoomTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@AllArgsConstructor
@RequestMapping("/rooms-type")
public class TypeRoomController {
    @Autowired
    private final IRoomTypeService typeRoomService;

    @Operation(summary = "Get all the list of type of room in paginated form")
    @ApiResponse(responseCode = "400", description = "When the connection with the data base fail", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))})
    @GetMapping
    public ResponseEntity<Page<RoomTypeResponse>> getAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size){
        return ResponseEntity.ok(this.typeRoomService.getAll(page - 1, size));
    }


    @Operation(summary = "Get a bookings find with a id")
    @ApiResponse(responseCode = "400", description = "When the id is not valid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeResponse> getById(@PathVariable String id) {
       return ResponseEntity.ok(this.typeRoomService.findById(id));
    }


    @Operation(summary = "This method create a type room with the dates sent")
    @ApiResponse(responseCode = "400", description = "When there is an error in the date sent to the datebase", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))})
    @PostMapping
    public ResponseEntity<RoomTypeResponse> insert(@Validated @RequestBody RoomTypeRequest typeRoom) {
       return ResponseEntity.ok(this.typeRoomService.create(typeRoom));
    }

    @Operation(summary = "This method allows you delete a type room for a id especific")
    @ApiResponse(responseCode = "400", description = "When the id it's not valid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String id){
        Map<String, String> response = new HashMap<>();
        response.put("message", "Type of room correctly eliminated");
        this.typeRoomService.delete(id);
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "This method allows you modify a type room for a id especific")
    @ApiResponse(responseCode = "400", description = "When the id it's not valid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    @PutMapping("/{id}")
    public ResponseEntity<RoomTypeResponse> update(@Validated @PathVariable String id, @RequestBody RoomTypeRequest typeRoom) {
        return ResponseEntity.ok(this.typeRoomService.update(typeRoom, id));
    }
    
}
