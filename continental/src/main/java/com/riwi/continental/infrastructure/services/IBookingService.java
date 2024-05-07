package com.riwi.continental.infrastructure.services;

import com.riwi.continental.api.dto.request.BookingRequest;
import com.riwi.continental.api.dto.response.BookingResponse;
import com.riwi.continental.domain.entities.Booking;
import com.riwi.continental.infrastructure.abstract_services.IBaseService;

public interface IBookingService extends IBaseService< BookingRequest, BookingResponse,String> {
    
}
