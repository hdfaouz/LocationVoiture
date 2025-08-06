package com.enaa.locatiovoitures.Services;

import com.enaa.locatiovoitures.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminData implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
}
