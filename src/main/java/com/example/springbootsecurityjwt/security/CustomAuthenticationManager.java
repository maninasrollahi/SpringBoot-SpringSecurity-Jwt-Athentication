package com.example.springbootsecurityjwt.security;

import com.example.springbootsecurityjwt.security.baseInfo.entity.Users;
import com.example.springbootsecurityjwt.security.baseInfo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final UsersRepository usersRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        String username = String.valueOf(auth.getPrincipal());
        String password = String.valueOf(auth.getCredentials());
        Users users = usersRepository.findByEmail(username).orElse(null);

        if(username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()){
            throw new BadCredentialsException("email or password must not be empty");
        }
        if(users == null){
            throw new BadCredentialsException("user not found");
        }
        if(!password.equals(users.getPassword())){
            throw new BadCredentialsException("password not match");
        }

        return new UsernamePasswordAuthenticationToken(users, users.getPassword(), users.getAuthorities());
    }
}
