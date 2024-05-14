package com.riwi.continental.infrastructure.abstract_services;

import com.riwi.continental.api.dto.request.BookingRequest;
import com.riwi.continental.api.dto.response.BookingResponse;
import com.riwi.continental.domain.entities.Booking;

public interface IBookingService extends IBaseService< BookingRequest, BookingResponse,String> {
    
}
