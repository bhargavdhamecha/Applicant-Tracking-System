package com.Security.auth;


import com.Security.dto.RoleDto;
import com.Security.token.Token;
import com.Security.token.TokenType;
import com.Security.config.JwtService;
import com.Security.dto.RegisterRequest;
import com.Security.entity.User;
import com.Security.repository.TokenRepository;
import com.Security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public String register(RegisterRequest request) {
        Optional<User> optionalUser = repository.findByEmail(request.getEmail());
        if (!optionalUser.isPresent()) {
            User user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .gender(request.getGender())
                    .role(request.getRole())
                    .dob(request.getDob())
                    .build();
            log.info("Created user {} ", user);
            repository.save(user);
            return "User created";
        } else {
            return "Email already registered";
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Optional<User> user = repository.findByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user.get());
//        revokeAllUserTokens(user.get());
//        saveUserToken(user.get(), jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .uid(user.get().getUid())
                .build();

    }

//    private void saveUserToken(User user, String jwtToken) {
//        Token token = Token.builder()
//                .user(user)
//                .token(jwtToken)
//                .tokenType(TokenType.BEARER)
//                .expired(false)
//                .revoked(false)
//                .build();
//        tokenRepository.save(token);
//    }
//
//    private void revokeAllUserTokens(User user) {
//        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUid());
//        if (validUserTokens.isEmpty())
//            return;
//        validUserTokens.forEach(token -> {
//            token.setExpired(true);
//            token.setRevoked(true);
//        });
//        tokenRepository.saveAll(validUserTokens);
//
//    }
}
