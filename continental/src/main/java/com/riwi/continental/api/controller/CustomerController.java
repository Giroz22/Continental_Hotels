package com.riwi.continental.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.continental.api.dto.response.CustomerResponse;
import com.riwi.continental.infrastructure.abstract_services.ICustomerService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/customer")
@AllArgsConstructor
public class CustomerController {
  private final ICustomerService iCustomerService;

  @GetMapping
  public ResponseEntity<Page<CustomerResponse>> getAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size){
    return ResponseEntity.ok(this.iCustomerService.getAll(page - 1, size));
  }
}
