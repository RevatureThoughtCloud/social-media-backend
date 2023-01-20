package com.revature.services;

import com.revature.models.PasswordToken;
import com.revature.repositories.PasswordTokenRepository;
import com.revature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class PasswordTokenService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordTokenRepository passwTokenRepo;

    public Optional<PasswordToken> createPasswordToken(String email) {
        String passwordToken;
        boolean processed = false;


        // setup and produce a digest based on the current time
        MessageDigest instance;
        try {
            instance = MessageDigest.getInstance("MD5");
            byte[] messageDigest = instance.digest(String.valueOf(System.nanoTime()).getBytes());
            StringBuilder hexString = new StringBuilder();

            // process digest to create a 'unique' string to use as token
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    // could use a for loop, but we're only dealing with a single
                    // byte
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            passwordToken = hexString.toString();


            return passwTokenRepo.createPasswordToken(passwordToken, email, processed);
        } catch(NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
            return null;
        }

        public Boolean process() {

            return false;
        }




}
