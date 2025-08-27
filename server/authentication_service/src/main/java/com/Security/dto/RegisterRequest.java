package com.Security.dto;

import com.Security.entity.Gender;
import com.Security.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest implements Serializable {
    @NotNull(message = "firstname should not be null")
    @Size(max = 30)
    private String firstname;
    @NotNull(message = "lastname should not be null")
    @Size(max = 30)
    private String lastname;
    @Email(message = "email should  be proper format")
    @NotNull
    private String email;
    @NotNull(message = "dob should not specify")
    private LocalDate dob;
    private Boolean isActive;
    @NotNull(message = "password can not be null")
    @Size(min = 8, max = 16, message = "password can must be greater than 8 characters and less than 16")
    private String password;
    @NotNull(message = "role can not be null")
    private Role role;
    @NotNull(message = "gender is mandatory")
    private Gender gender;
}
