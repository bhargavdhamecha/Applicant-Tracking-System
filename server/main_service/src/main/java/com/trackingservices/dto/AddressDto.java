package com.trackingservices.dto;

import com.trackingservices.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto implements Serializable {
//    private int addressId;
    private String street;
    private String country;
    private String state;
    private String city;
    private Integer pincode;
    private String addressType;
    private User user;

}
