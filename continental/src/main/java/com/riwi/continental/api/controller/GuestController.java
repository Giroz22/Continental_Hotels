package com.riwi.continental.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.continental.api.dto.request.GuestRequest;
import com.riwi.continental.api.dto.response.GuestResponse;
import com.riwi.continental.infrastructure.abstract_services.IGuestService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/guest")
@AllArgsConstructor
public class GuestController {

  private final IGuestService iGuestService;

  @GetMapping
  public ResponseEntity<Page<GuestResponse>> getAll(
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "5") int size
  ){

    return ResponseEntity.ok(this.iGuestService.getAll(page, size));
  }

  @PostMapping
  public ResponseEntity<GuestResponse> insert (
    @RequestBody GuestRequest guest){
      return ResponseEntity.ok(this.iGuestService.create(guest));
    }

  @GetMapping(path = "/{id}")
  public ResponseEntity<GuestResponse> get(
    @PathVariable String id
  ){
    return ResponseEntity.ok(this.iGuestService.findById(id));
  }
}
