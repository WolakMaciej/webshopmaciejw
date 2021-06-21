package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.model.UserAuthority;
import com.example.demo.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Start {

    public Start(UserRepository userRepository, PasswordEncoder passwordEncoder){
        User userAdmin = new User();
        userAdmin.setId(1L);
        userAdmin.setUsername("Admin");
        userAdmin.setPassword(passwordEncoder.encode("admin123"));
        userAdmin.setEmail("admin@gmail.com");
        userAdmin.setAuthority(UserAuthority.ADMIN);
        userRepository.save(userAdmin);

        User userUser = new User();
        userUser.setId(2L);
        userUser.setUsername("User");
        userUser.setPassword(passwordEncoder.encode("user1234"));
        userUser.setAuthority(UserAuthority.USER);
        userRepository.save(userUser);
    }
}
