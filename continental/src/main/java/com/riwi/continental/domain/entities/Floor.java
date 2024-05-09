package com.riwi.continental.domain.entities;

import com.riwi.continental.util.enums.StatusFloor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "floor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private int number;

    @Column(length = 25, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusFloor statusFloor;

    @Column(nullable = false)
    private int numberOfRooms;

    // @OneToMany(mappedBy = "floors", fetch = FetchType.EAGER, cascade =
    // CascadeType.ALL, orphanRemoval = false)
    // @ToString.Exclude
    // @EqualsAndHashCode.Exclude
    // private List<Room> rooms;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotels_id", referencedColumnName = "id")
    private Hotel hotel;

}