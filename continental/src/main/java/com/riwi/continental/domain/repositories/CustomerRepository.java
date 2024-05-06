package com.riwi.continental.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.continental.domain.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository <Customer, String>{

}
