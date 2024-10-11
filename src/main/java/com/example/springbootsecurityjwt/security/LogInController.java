package com.example.springbootsecurityjwt.security;

import com.example.springbootsecurityjwt.security.baseInfo.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@RestController
@RequestMapping("/auth")
@Log
@RequiredArgsConstructor
public class LogInController {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/logIn", consumes = "application/json")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> credential) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            String username = credential.get("username").toString();
            String password = credential.get("password").toString();
            Authentication auth =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            if (auth.isAuthenticated()) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                Users logInUser = (Users) auth.getPrincipal();
                String token = jwtService.generateToken(userDetails);

                responseMap.put("error", false);
                responseMap.put("message", "Logged In");
                responseMap.put("token", token);
                responseMap.put("userId", logInUser.getId());

                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("error", true);
                responseMap.put("message", "Invalid Credential");
                return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(responseMap);
            }
        } catch (BadCredentialsException e) {
            responseMap.put("error", true);
            responseMap.put("message", e.getMessage());
            log.log(Level.WARNING, e.getMessage(), e);
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(responseMap);
        }
    }
}
