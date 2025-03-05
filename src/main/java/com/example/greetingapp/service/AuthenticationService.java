package com.example.greetingapp.service;

import com.example.greetingapp.DTO.AuthUserDTO;
import com.example.greetingapp.DTO.LoginDTO;
import com.example.greetingapp.config.JwtUtil;
import com.example.greetingapp.entity.AuthUser;
import com.example.greetingapp.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import javax.mail.internet.MimeMessage;

@Service
public class AuthenticationService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JavaMailSender mailSender;

    @Autowired
    public AuthenticationService(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, JavaMailSender mailSender) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.mailSender = mailSender;
    }

    @Transactional
    public String registerUser(AuthUserDTO authUserDTO) {
        if (authUserRepository.findByEmail(authUserDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        AuthUser user = new AuthUser();
        user.setFirstName(authUserDTO.getFirstName());
        user.setLastName(authUserDTO.getLastName());
        user.setEmail(authUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(authUserDTO.getPassword()));

        authUserRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return "Registration successful, token: " + token; // Return token in response
    }

    public String loginUser(LoginDTO loginDTO) {
        Optional<AuthUser> userOptional = authUserRepository.findByEmail(loginDTO.getEmail());

        if (userOptional.isEmpty() || !passwordEncoder.matches(loginDTO.getPassword(), userOptional.get().getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        sendLoginNotification(userOptional.get().getEmail());
        String token = jwtUtil.generateToken(userOptional.get().getEmail());
        return "Login successful, token: " + token; // Return token in response
    }

    public void sendLoginNotification(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Login Notification");
        message.setText("Hello,\n\nYou have successfully logged into your account.\n\nIf this wasn't you, please secure your account.");
        mailSender.send(message);
        System.out.println("Login notification email sent to: " + email);
    }

}
