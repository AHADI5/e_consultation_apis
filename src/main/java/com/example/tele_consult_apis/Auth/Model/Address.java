package com.example.tele_consult_apis.Auth.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private String quarter   ;
    private String avenue  ;
    private int houseNumber  ;
    @OneToOne(mappedBy = "address")
    private User user;





}
