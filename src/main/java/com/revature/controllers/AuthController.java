package com.revature.controllers;

import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.PasswordTokenService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000", "http://p3-dist.s3-website-us-east-1.amazonaws.com" }, allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    private final PasswordTokenService passwTokenService;

    public AuthController(AuthService authService, PasswordTokenService passwTokenService) {
        this.authService = authService;
        this.passwTokenService = passwTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Optional<User> optional = authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword());

        if (!optional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        session.setAttribute("user", optional.get());

        return ResponseEntity.ok(optional.get());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.removeAttribute("user");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        User created = new User(
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getUserName());

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(created));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Boolean> sendResetEmail(@RequestBody String userEmail) {

        return ResponseEntity.ok(passwTokenService.createPasswordToken(userEmail).isPresent());
    }

    @PostMapping(value = "/reset-password", params = {"validate"})
    public ResponseEntity<Boolean> resetPassword(@RequestBody String userEmail) {




        return ResponseEntity.ok(true);
    }

}
