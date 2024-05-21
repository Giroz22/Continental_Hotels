package com.riwi.continental.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.continental.api.dto.errors.ErrorResponse;
import com.riwi.continental.api.dto.errors.ErrorsResponse;
import com.riwi.continental.api.dto.request.CustomerRequest;
import com.riwi.continental.api.dto.response.CustomerResponse;
import com.riwi.continental.domain.entities.Customer;
import com.riwi.continental.infrastructure.abstract_services.ICustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/customer")
@AllArgsConstructor
public class CustomerController {
  private final ICustomerService iCustomerService;

  @Operation(summary = "this method allows get all the list of customer in paginated form")
  @ApiResponse(responseCode = "400", description = "When the connection with the data base fail", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class)) })
  @GetMapping
  public ResponseEntity<Page<CustomerResponse>> getAll(@RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "3") int size) {
    return ResponseEntity.ok(this.iCustomerService.getAll(page - 1, size));
  }

  @Operation(summary = "This method allows create a customer with the dates sent")
  @ApiResponse(responseCode = "400", description = "When there is an error in the date sent to the datebase", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class)) })
  @PostMapping
  public ResponseEntity<CustomerResponse> insert(
      @Validated @RequestBody CustomerRequest customer) {
    return ResponseEntity.ok(this.iCustomerService.create(customer));
  }

  @GetMapping(path = "/profile")
  public ResponseEntity<Customer> getCustomerProfile(@RequestHeader("Authorization") String token) {
    try {
      token = token.replace("Bearer ", "");

      Customer customer = iCustomerService.getUser(token);

      if (customer == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }
      return ResponseEntity.ok(customer);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

  }

  @Operation(summary = "this method allows get a customer find with a id")
  @ApiResponse(responseCode = "400", description = "When the id is not valid", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @GetMapping(path = "/{id}")
  public ResponseEntity<CustomerResponse> get(
      @PathVariable String id) {
    return ResponseEntity.ok(this.iCustomerService.findById(id));
  }

  @Operation(summary = "This method allows you delete a customer for a id especific")
  @ApiResponse(responseCode = "400", description = "When the id it's not valid", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(
      @PathVariable String id) {
    this.iCustomerService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "This method allows you modify a customer for a id especific")
  @ApiResponse(responseCode = "400", description = "When the id it's not valid", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @PutMapping(path = "/{id}")
  public ResponseEntity<CustomerResponse> update(
      @Validated @PathVariable String id,
      @RequestBody CustomerRequest customer) {
    return ResponseEntity.ok(this.iCustomerService.update(customer, id));
  }
}
