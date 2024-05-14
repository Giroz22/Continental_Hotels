package com.riwi.continental.domain.entities;

import com.riwi.continental.util.enums.AgeCategory;

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

@Entity(name = "guest")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 15, nullable = false)
    private String idDocument;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 45, nullable = false)
    private String lastname;

    @Column(nullable = false)
    private int age;

    @Enumerated(EnumType.STRING)
    private AgeCategory ageCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id" , referencedColumnName = "id")
    private Booking booking;
}
