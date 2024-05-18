package com.riwi.continental.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.riwi.continental.api.dto.request.GuestRequest;
import com.riwi.continental.api.dto.response.GuestResponse;
import com.riwi.continental.infrastructure.abstract_services.IGuestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/guest")
@AllArgsConstructor
public class GuestController {

  private final IGuestService iGuestService;

  @Operation(summary = "Get all the list of guest in paginated form")
    @ApiResponse(responseCode = "400", description = "When the connection with the data base fail", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))})
  @GetMapping
  public ResponseEntity<Page<GuestResponse>> getAll(
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "5") int size
  ){

    return ResponseEntity.ok(this.iGuestService.getAll(page - 1, size));
  }

  @Operation(summary = "This method allows create a guest with the dates sent")
    @ApiResponse(responseCode = "400", description = "When there is an error in the date sent to the datebase", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))})
  @PostMapping
  public ResponseEntity<GuestResponse> insert (
    @Validated @RequestBody GuestRequest guest){
      return ResponseEntity.ok(this.iGuestService.create(guest));
    }

  @Operation(summary = "this method allows get a guest find with a id")
    @ApiResponse(responseCode = "400", description = "When the id is not valid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
  @GetMapping(path = "/{id}")
  public ResponseEntity<GuestResponse> get(
    @PathVariable String id
  ){
    return ResponseEntity.ok(this.iGuestService.findById(id));
  }

  @Operation(summary = "This method allows you delete a guest for a id especific")
    @ApiResponse(responseCode = "400", description = "When the id it's not valid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Map<String, String>> delete(
    @PathVariable String id){
      Map<String, String> response = new HashMap<>();

      response.put("message","guest successfully removed");

      this.iGuestService.delete(id);

      return ResponseEntity.ok(response);
    }

    @Operation(summary = "This method allows you modify a guest for a id especific")
    @ApiResponse(responseCode = "400", description = "When the id it's not valid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
  @PutMapping(path = "/{id}")
  public ResponseEntity<GuestResponse> update(
    @Validated @PathVariable String id,
    @RequestBody GuestRequest guest){
      return ResponseEntity.ok(iGuestService.update(guest, id));
    }
}
