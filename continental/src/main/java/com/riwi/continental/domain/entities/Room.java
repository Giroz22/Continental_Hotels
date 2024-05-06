package com.riwi.continental.domain.entities;

import com.riwi.continental.util.enums.StateRoom;

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

@Entity(name = "room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String room_id;

    @Enumerated(EnumType.STRING)
    private StateRoom state;

    private int num;
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomType_id", referencedColumnName = "roomType_id")
    private RoomType roomType;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "floor_id", referencedColumnName = "floor_id")
    // private Floor floor;

}
