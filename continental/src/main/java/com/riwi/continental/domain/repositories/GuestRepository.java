package com.riwi.continental.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.continental.domain.entities.Guest;

public interface GuestRepository extends JpaRepository <Guest, String> {

}
