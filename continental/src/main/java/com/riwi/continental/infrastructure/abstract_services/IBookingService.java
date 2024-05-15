package com.riwi.continental.infrastructure.abstract_services;

import java.util.List;

import com.riwi.continental.api.dto.request.BookingRequest;
import com.riwi.continental.api.dto.response.BookingResponse;

public interface IBookingService extends IBaseService< BookingRequest, BookingResponse,String> {

    List getCustomerBooking(String id);
    
}
