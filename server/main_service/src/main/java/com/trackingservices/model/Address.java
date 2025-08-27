package com.trackingservices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;
    @Column(name = "street")
    @NotNull
    private String street;
    @Column(name = "country")
    @NotNull
    private String country;
    @Column(name = "state")
    @NotNull
    private String state;
    @Column(name = "city")
    @NotNull
    private String city;

    @Column(name = "pincode")
    @NotNull
    private Integer pincode;
    @Column(name = "adressType")
    @NotNull
    private String addressType;

    @ManyToOne
    @JoinColumn(name="fr_uid")
    @JsonIgnore
    private User user;


    @Override
    public String toString() {
        return "Address{" +
                "address_id=" + addressId +
                ", street='" + street + '\'' +
                ", permanent_country='" +country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", pincode=" + pincode +
                ", addressType='" + addressType + '\'' +
                '}';
    }

}
