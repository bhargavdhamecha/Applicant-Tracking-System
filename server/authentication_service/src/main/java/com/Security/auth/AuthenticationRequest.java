package com.Security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest implements Serializable {
  @NotNull(message = "email not be null")
  @Email(message = "email should in proper format")
  private String email;
  @NotNull(message = "password cn not be null")
  private String password;
}
