package com.example.tele_consult_apis.Auth.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProfilePic {
    @Id
    @GeneratedValue(generator = "uuid")
    private String id ;
    private String name ;
    private String type  ;
    @Lob
    private byte[] data;
    @OneToOne(mappedBy = "profilePic")
    private User user ;

}
