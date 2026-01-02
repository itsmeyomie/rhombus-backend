package com.rhombus.concrete.config;

import com.rhombus.concrete.entity.User;
import com.rhombus.concrete.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPasswordHash("admin123");
            admin.setRole("admin");
            userRepository.save(admin);

            User security = new User();
            security.setUsername("security");
            security.setPasswordHash("security123");
            security.setRole("security");
            userRepository.save(security);
        }
    }
}



