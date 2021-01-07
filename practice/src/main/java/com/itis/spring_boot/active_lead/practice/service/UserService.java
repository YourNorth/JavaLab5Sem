package com.itis.spring_boot.active_lead.practice.service;

import com.itis.spring_boot.active_lead.practice.model.User;
import com.itis.spring_boot.active_lead.practice.repositories.UserRepository;
import com.itis.spring_boot.active_lead.practice.utils.CustomUserDetails;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@NoArgsConstructor
@Service
public class UserService implements UserDetailsService{

    private CustomUserDetails userDetails;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(s);
        if(user.isPresent())
            return new CustomUserDetails(user.get());
        else{
            throw new UsernameNotFoundException("Bad credentials");
        }
    }
}
