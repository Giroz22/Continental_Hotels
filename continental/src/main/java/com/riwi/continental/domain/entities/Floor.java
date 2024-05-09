package com.riwi.continental.domain.entities;

import com.riwi.continental.util.enums.StatusFloor;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity(name = "floor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 15, nullable = false)
    private int number;

    @Column(length = 25, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusFloor statusFloor;

    @Column(length = 2, nullable = false)
    private int numberOfRooms;

    @OneToMany(mappedBy = "floor", fetch = FetchType.EAGER, cascade =
    CascadeType.ALL, orphanRemoval = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Room> rooms;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "hotels_id", referencedColumnName = "id")
    // private Hotel hotel;

}