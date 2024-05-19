package com.riwi.continental.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

import com.riwi.continental.api.dto.request.GuestRequest;
import com.riwi.continental.api.dto.response.GuestResponse;
import com.riwi.continental.infrastructure.abstract_services.IGuestService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/guest")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
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

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Map<String, String>> delete(
    @PathVariable String id){
      Map<String, String> response = new HashMap<>();

      response.put("message","guest successfully removed");

      this.iGuestService.delete(id);

      return ResponseEntity.ok(response);
    }

  @PutMapping(path = "/{id}")
  public ResponseEntity<GuestResponse> update(
    @PathVariable String id,
    @RequestBody GuestRequest guest){
      return ResponseEntity.ok(iGuestService.update(guest, id));
    }
}
