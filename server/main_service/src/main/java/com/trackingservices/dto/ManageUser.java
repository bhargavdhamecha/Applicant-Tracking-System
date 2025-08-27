package com.trackingservices.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ManageUser {
    private Long id;

    private String firstname;
    private String lastname;
    private String email;
    private Long phoneNumber;
    private Boolean isActive;

    private String rolename;
    public ManageUser(Long id,String email, Long phone_number, String firstname, String lastname, Boolean isActive, String rolename) {
        this.id = id;
        this.firstname = firstname;
        this.lastname=lastname;
        this.email = email;
        this.phoneNumber = phone_number;
        this.isActive = isActive;
        this.rolename = rolename;
    }
}
