package com.Security.auth;

import com.Security.dto.EmailRequest;
import com.Security.dto.IdPassword;
import com.Security.dto.RegisterRequest;

import com.Security.entity.User;
import com.Security.repository.UserRepository;
import com.Security.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/authentication/api/v1/auth")
@RequiredArgsConstructor
public class
AuthenticationController {

  private final AuthenticationService service;
  @Autowired
  private EmailService emailService;

  @Autowired
  private UserRepository userRepository;


  @PostMapping("/forgotPassword")
  public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {

    try {
      User user = userRepository.findByEmail(email);
      if (user == null) {
        throw new RuntimeException("User not found");
      }
      Long uId = user.getUid();

      String message = "https://192.168.102.92:8011/reset-password?uId=" + uId;
      String subject = "Please reset your password";
      EmailRequest forgotPasswordRequest = new EmailRequest(email, subject, message);
      boolean status = emailService.sendEmail(forgotPasswordRequest);
      if (status) {
        System.out.println("email sent seccessfully");
        return new ResponseEntity<>(HttpStatus.OK);
      } else {
        System.out.println("Email not send");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    } catch (Exception e) {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @PostMapping("/setPassword")
  public ResponseEntity<HttpStatus> setPassword(@RequestBody IdPassword idPassword) {
    Optional<User> userOptional = userRepository.findByUid(idPassword.getUId());

    if (userOptional.isPresent()) {
      User user = userOptional.get();
      user.setPassword(idPassword.getPassword());
      System.out.println("Password updated");
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      System.out.println("Password not updated");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
    return ResponseEntity.ok(service.register(request));
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }
//  @GetMapping("/getPermissions/{permission}")
//  public ResponseEntity<List<Permission>> getPermissions(@RequestBody  Integer id){
//
//  }


}
