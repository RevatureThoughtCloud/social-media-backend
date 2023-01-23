package com.revature.controllers;

import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.dtos.ResetEmailRequest;
import com.revature.dtos.ResetPasswRequest;
import com.revature.models.PasswordToken;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.PasswordTokenService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import com.revature.utilities.EmailUtil;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000",
        "http://p3-dist.s3-website-us-east-1.amazonaws.com/" }, allowCredentials = "true", allowedHeaders = "*")
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
    public ResponseEntity<Boolean> sendResetEmail(@RequestBody ResetEmailRequest reqBody) {
        Optional<User> linkedUser = authService.getUserByEmail(reqBody.getUserEmail());
        if (linkedUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Optional<PasswordToken> tokenOptional = passwTokenService.createPasswordToken(linkedUser.get());
        if (tokenOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        PasswordToken passwordToken = tokenOptional.get();

        JavaMailSender emailSender = EmailUtil.getJavaMailSender();
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        String body = "<p>This email is in response to a ThoughtCloud Reset Password Request. </p>\n" +
                "<p>If you recognize this request and want to go ahead with changing your password follow this link: </p>\n" +
                String.format("<a href=\"http://p3-dist.s3-website-us-east-1.amazonaws.com/reset-password?token=%s\">Reset Password Link</a> ", passwordToken.getPasswordToken());
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            messageHelper.setFrom("thoughtcloud@mail.com");
            messageHelper.setTo(passwordToken.getUser().getEmail());
            messageHelper.setSubject("ThoughtCloud Reset Password Request");
            messageHelper.setText(body, true);
            emailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.printf("Email could not be sent to user '%s': %s ", passwordToken.getUser().getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(true);
    }

    @PostMapping(value = "/reset-password", params = {"token"})
    public ResponseEntity<Boolean> resetPassword(@RequestBody ResetPasswRequest newPassword, String token) {
        Optional<PasswordToken> passwordTokenOptional = passwTokenService.findPassToken(token);
        if (passwordTokenOptional.isEmpty() || passwordTokenOptional.get().isProcessed() || newPassword.getNewPassword() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        PasswordToken passwordToken = passwordTokenOptional.get();

        boolean result = passwTokenService.process(passwordToken, newPassword.getNewPassword());
        return ResponseEntity.ok(result);
    }

}
