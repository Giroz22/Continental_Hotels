package com.riwi.continental.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(length = 45, nullable = false)
  private String name;

  @Column(length = 45, nullable = false)
  private String lastname;

  @Column(nullable = false)
  private int age;

  @Column(length = 15, nullable = false)
  private String idDocument;

  @Column(length = 10, nullable = false)
  private String cellphone;

  // @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
  // private List<Booking> booking;

}