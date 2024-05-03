package com.riwi.continental.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.continental.infrastructure.abstract_services.IFloorService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/floors")
@AllArgsConstructor
public class FloorController {

    @Autowired
    private final IFloorService iFloorService;

}
