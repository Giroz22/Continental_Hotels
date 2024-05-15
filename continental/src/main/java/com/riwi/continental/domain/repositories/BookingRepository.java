package com.riwi.continental.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.riwi.continental.domain.entities.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String>{

    @Query(value = "SELECT * FROM booking WHERE id_customer = :id", nativeQuery = true)
    List<Booking> findCustomerBooking(@Param("id") String idCustomer);
}
