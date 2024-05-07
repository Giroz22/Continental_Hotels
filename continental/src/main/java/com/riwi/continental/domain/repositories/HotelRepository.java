package com.riwi.continental.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.continental.domain.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, String> {

}
