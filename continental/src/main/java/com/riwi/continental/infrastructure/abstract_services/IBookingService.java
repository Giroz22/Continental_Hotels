package com.riwi.continental.infrastructure.abstract_services;

import java.util.List;

import com.riwi.continental.api.dto.request.BookingRequest;
import com.riwi.continental.api.dto.request.GuestToBookingRequest;
import com.riwi.continental.api.dto.response.BookingResponse;

public interface IBookingService extends IBaseService< BookingRequest, BookingResponse,String> {
    public BookingResponse checkIn(String id, List<GuestToBookingRequest> listGuestRequest);
    public BookingResponse checkOut(String id, List<GuestToBookingRequest> listGuestRequest);
}
