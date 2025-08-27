package com.trackingservices.dto;

import com.trackingservices.model.Address;
import com.trackingservices.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class updateAddressDto {
    private String street;
    private String country;
    private String state;
    private String city;
    private Integer pincode;
    private String addressType;
    private User user;

    private Address address;

}
