package com.diway.springbootsecurity.services;

import com.diway.springbootsecurity.entity.User;
import com.diway.springbootsecurity.models.MyUserDetails;
import com.diway.springbootsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        optionalUser.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
        return optionalUser.map(MyUserDetails::new).get();
    }
}
