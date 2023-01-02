package com.springmvc.security;

import com.springmvc.persistence.entity.User;
import com.springmvc.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // user entity
        User user = userRepository.findFirstByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        return new CustomUserDetails(user);
    }
}
