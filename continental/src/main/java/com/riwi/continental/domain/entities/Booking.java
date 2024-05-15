package com.riwi.continental.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.riwi.continental.util.enums.StatusBooking;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "booking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private LocalDate admissionDate;
    private LocalDate departureDate;
    private LocalTime admissionTime;
    private LocalTime departureTime;
    private BigDecimal price;
    private StatusBooking status;
    
    @ManyToOne
    @JoinColumn(name = "id_customer", referencedColumnName = "id")
    private  Customer customer;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
        mappedBy = "booking",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    private List<Guest> guests; 
    
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
        mappedBy = "booking",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    private List<Room> rooms; 
}
