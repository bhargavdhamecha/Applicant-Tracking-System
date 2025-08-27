package com.trackingservices.dto;
import com.trackingservices.model.Role;
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
public class UserUpdateDetails implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
