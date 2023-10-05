package egar.egartask.service;

import egar.egartask.dto.ConsumerDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
private final UserDetailsService userDetailsService;
private final PasswordEncoder encoder;

    public AuthService(UserDetailsService userDetailsService, PasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;

    }
    public boolean login(ConsumerDTO consumerDTO){
        UserDetails userDetails = userDetailsService.loadUserByUsername(consumerDTO.getLogin());
        if (null==userDetails){
            return false;
        }
        return encoder.matches(consumerDTO.getPassword(),userDetails.getPassword());
    }
}
