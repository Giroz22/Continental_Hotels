package com.riwi.continental.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.continental.infrastructure.abstract_services.IHotelService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/Hotels")
@AllArgsConstructor
public class HotelController {

    @Autowired
    private final IHotelService iHotelService;
}
