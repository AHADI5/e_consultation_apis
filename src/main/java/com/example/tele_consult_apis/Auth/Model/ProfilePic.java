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
    @GeneratedValue
    private long id ;
    private String name ;
    private String type  ;

    @Lob
    @Column(name = "data", columnDefinition = "LONGBLOB")
    private byte[] data;
    @OneToOne(mappedBy = "profilePic")
    private User user ;

}
