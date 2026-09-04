package com.onboardassist.service;

import com.onboardassist.dto.LoginRequest;
import com.onboardassist.dto.LoginResponse;
import com.onboardassist.dto.RegisterRequest;
import com.onboardassist.entity.User;
import com.onboardassist.repository.UserRepository;
import com.onboardassist.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));
        String token = jwtUtil.generateToken(user);
        return new LoginResponse(token, user.getEmail(), user.getName(), user.getRole());
    }

    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already registered");
        }
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("USER");
        userRepository.save(user);
    }
}
