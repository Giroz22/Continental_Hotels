package com.riwi.continental.domain.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "hotels")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel{
  
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 80, nullable = false)
    private String ubication;

    @Column(length = 50, nullable = false)
    private String contact;

    @Column(length = 5, nullable = false)
    private double calification;

    @Column(length = 15, nullable = false)
    private double earnings;

    //@OneToMany(mappedBy = "hotels", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = false)
    //@ToString.Exclude
    //@EqualsAndHashCode.Exclude
    //private List<Floor> floors;
}