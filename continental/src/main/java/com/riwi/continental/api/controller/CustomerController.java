package com.riwi.continental.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.continental.api.dto.request.CustomerRequest;
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

  @PostMapping
  public ResponseEntity<CustomerResponse> insert (
    @RequestBody CustomerRequest customer){
      return ResponseEntity.ok(this.iCustomerService.create(customer));
    }

  @GetMapping(path = "/{id}")
  public ResponseEntity<CustomerResponse> get(
    @PathVariable String id ){
      return ResponseEntity.ok(this.iCustomerService.findById(id));
    }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete (
    @PathVariable String id){
      this.iCustomerService.delete(id);
      return ResponseEntity.noContent().build();
    }  
  
    @PutMapping(path = "/{id}")
    public ResponseEntity<CustomerResponse> update(
      @PathVariable String id,
      @RequestBody CustomerRequest customer){
        return ResponseEntity.ok(this.iCustomerService.update(customer, id));
      }
}
