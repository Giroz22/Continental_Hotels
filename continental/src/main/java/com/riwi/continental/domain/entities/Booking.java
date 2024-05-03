package com.riwi.continental.domain.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.mapping.List;

import com.riwi.continental.util.enums.StatusBooking;

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
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Double valor;
    private StatusBooking estado;
    private LocalDate fecha_ingreso;
    private LocalDate fecha_salida;
    private LocalTime hora_entrada;
    private LocalTime hora_salida;

    // @OneToMany
    // @JoinColumn(mappeBy = "booking", fetch = FetchType.EAGER)
    // private List<Guets>;

    // @ManyToOne
    // @JoinColumn(name = "id_customer", referencedColumnName = "id")
    // private List<Customer>;

    
}
