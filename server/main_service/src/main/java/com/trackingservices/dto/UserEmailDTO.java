package com.trackingservices.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class UserEmailDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public UserEmailDTO(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserEmailDTO{" +
               "id=" + id +
               ", firstname='" + firstName + '\'' +
               ", lastname='" + lastName + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}
