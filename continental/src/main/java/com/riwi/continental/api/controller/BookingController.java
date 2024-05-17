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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.continental.api.dto.errors.ErrorResponse;
import com.riwi.continental.api.dto.errors.ErrorsResponse;
import com.riwi.continental.api.dto.request.BookingRequest;
import com.riwi.continental.api.dto.response.BookingResponse;
import com.riwi.continental.infrastructure.abstract_services.IBookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(path = "/booking")
@AllArgsConstructor
public class BookingController {

    @Autowired
    private final IBookingService bookingService;

    @Operation(summary = "Get all the list of bookings in paginated form")
    @ApiResponse(responseCode = "400", description = "When the connection with the data base fail", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class)) })
    @GetMapping
    public ResponseEntity<Page<BookingResponse>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok(this.bookingService.getAll(page - 1, size));
    }

    @Operation(summary = "Get all the bookings find with a id")
    @ApiResponse(responseCode = "400", description = "When the id is not valid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    @GetMapping(path = "/{id}")
    public ResponseEntity<BookingResponse> get(
            @PathVariable String id) {
        return ResponseEntity.ok(this.bookingService.findById(id));
    }

    @Operation(summary = "This method create a booking with the dates sent")
    @ApiResponse(responseCode = "400", description = "When there is an error in the date sent to the datebase", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class)) })
    @PostMapping
    public ResponseEntity<BookingResponse> insert(
            @Validated @RequestBody BookingRequest booking) {
        this.bookingService.create(booking);
        return ResponseEntity.ok(this.bookingService.create(booking));
    }

    @Operation(summary = "This method allows you modify a booking for a id especific")
    @ApiResponse(responseCode = "400", description = "When the id it's not valid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    @PutMapping(path = "/{id}")
    public ResponseEntity<BookingResponse> update(
            @PathVariable String id,
            @Validated @RequestBody BookingRequest booking) {
        return ResponseEntity.ok(this.bookingService.update(booking, id));
    }

    @Operation(summary = "This method allows you delete a booking for a id especific")
    @ApiResponse(responseCode = "400", description = "When the id it's not valid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        this.bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
