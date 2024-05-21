package com.riwi.continental.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.continental.api.dto.errors.ErrorResponse;
import com.riwi.continental.api.dto.errors.ErrorsResponse;
import com.riwi.continental.api.dto.request.HotelRequest;
import com.riwi.continental.api.dto.response.HotelResponse;
import com.riwi.continental.infrastructure.abstract_services.IHotelService;

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
@RequestMapping(path = "/hotels")
@AllArgsConstructor
public class HotelController {

    @Autowired
    private final IHotelService iHotelService;

    @Operation(summary = "this method allows get all the list of hotels in paginated form")
    @ApiResponse(responseCode = "400", description = "When the connection with the data base fail", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))})
    @GetMapping
    public ResponseEntity<Page<HotelResponse>> showHotels(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok(this.iHotelService.getAll(page - 1, size));
    }

    @Operation(summary = "This method allows create a hotel with the dates sent")
    @ApiResponse(responseCode = "400", description = "When there is an error in the date sent to the datebase", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))})
    @PostMapping
    public ResponseEntity<HotelResponse> addHotel(@RequestBody HotelRequest hotelRequest) {

        return ResponseEntity.ok(this.iHotelService.create(hotelRequest));
    }

    @Operation(summary = "this method allows get a hotel find with a id")
    @ApiResponse(responseCode = "400", description = "When the id is not valid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    @GetMapping(path = "/{id}")
    public ResponseEntity<HotelResponse> filterById(@PathVariable String id) {
        return ResponseEntity.ok(this.iHotelService.findById(id));
    }

    @Operation(summary = "This method allows you modify a hotel for a id especific")
    @ApiResponse(responseCode = "400", description = "When the id it's not valid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    @PutMapping(path = "/{id}")
    public ResponseEntity<HotelResponse> updateHotel(@PathVariable String id, @RequestBody HotelRequest hotelRequest) {
        return ResponseEntity.ok(this.iHotelService.update(hotelRequest, id));
    }

    @Operation(summary = "This method allows you delete a hotel for a id especific")
    @ApiResponse(responseCode = "400", description = "When the id it's not valid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String id) {
        this.iHotelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
