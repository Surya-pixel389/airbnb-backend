package com.aribnb.backend.service;

import com.aribnb.backend.config.JwtUtil;
import com.aribnb.backend.dto.AuthResponse;
import com.aribnb.backend.dto.LoginRequest;
import com.aribnb.backend.dto.RegisterRequest;
import com.aribnb.backend.entity.UserType;
import com.aribnb.backend.entity.user;
import com.aribnb.backend.repository.UserRespository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRespository userRespository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder password = new BCryptPasswordEncoder();

    public AuthResponse register(RegisterRequest request) {
        if (userRespository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
    }
        String hashedPassword = password.encode(request.getPassword());
        user usr = user.builder()
                .email(request.getEmail())
                .password(hashedPassword)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .userType(UserType.valueOf(request.getUserType().toUpperCase()))
                .verified(false)
                .build();
        user savedUser = userRespository.save(usr);
        String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getId());

        return new  AuthResponse(
            token,"Success", savedUser.getId(), savedUser.getEmail()
        );
    }
    public AuthResponse login(LoginRequest request){
        Optional<user> userOpt = userRespository.findByEmail(request.getEmail());
        if(userOpt.isEmpty())
            throw new RuntimeException("User Not Found");

        user usr = userOpt.get();
        if(!password.matches(request.getPassword(), usr.getPassword()))
            throw new RuntimeException("Password Wrong");

        String token = jwtUtil.generateToken(usr.getEmail(), usr.getId());

        return new AuthResponse(token, "Login Success", usr.getId(), usr.getEmail());
    }
        public Optional<user> getUserByEmail(String email){
            return userRespository.findByEmail(email);
        }
}
