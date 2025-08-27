package com.trackingservices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


enum Gender{
    MALE,FEMALE,OTHER
}
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_user")
@Builder
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Long uid;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "dob")
    private LocalDate dob;
    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number")
    private Long phoneNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToOne
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Address.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "fr_uid", referencedColumnName = "uid")
    private List<Address> address;
    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;

    public User(long l) {
        this.uid=l;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", role=" + role +
                '}';
    }

}
