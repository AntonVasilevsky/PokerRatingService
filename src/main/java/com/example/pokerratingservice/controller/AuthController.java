package com.example.pokerratingservice.controller;

import com.example.pokerratingservice.service.CustomerDetailsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AuthController {
    final CustomerDetailsService customerDetailsService;
    @PostMapping("/process-login")
    public ResponseEntity<?> processLogin(@RequestParam String email, @RequestParam String password) {

        UserDetails userDetails = customerDetailsService.loadUserByUsername(email);

            return ResponseEntity.ok(userDetails);

    }
}
