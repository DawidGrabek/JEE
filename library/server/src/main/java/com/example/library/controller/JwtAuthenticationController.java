package com.example.library.controller;
import com.example.library.config.JwtTokenUtil;

import java.util.Map;
import java.util.Objects;

import com.example.library.entity.LibraryUser;
import com.example.library.model.JwtRequest;
import com.example.library.model.JwtResponse;
import com.example.library.service.JwtUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/api/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody
                                                       JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(),
                authenticationRequest.getPassword());
        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(authenticationRequest
                        .getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        String username = userDetails.getUsername();

        return ResponseEntity.ok(Map.of("token", token, "username", username));
//        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/api/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@Valid @RequestBody LibraryUser user) {
        try {
            return ResponseEntity.ok(userDetailsService.save(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}