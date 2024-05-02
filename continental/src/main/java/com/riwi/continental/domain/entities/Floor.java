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

@Entity(name = "floors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Floor{
  
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 15, nullable = false)
    private int number;

    @Column(length = 25, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusFloor statusFloor;


    //@OneToMany(mappedBy = "floors", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = false)
    //@ToString.Exclude
    //@EqualsAndHashCode.Exclude
    //private List<Room> rooms;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "hotels_id", referencedColumnName = "id")
    //private Hotel hotel;


}