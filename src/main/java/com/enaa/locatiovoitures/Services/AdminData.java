package com.enaa.locatiovoitures.Services;

import com.enaa.locatiovoitures.Model.Admin;
import com.enaa.locatiovoitures.Model.Role;
import com.enaa.locatiovoitures.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
public class AdminData implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {

        String adminEmail = "aitloutar@gmail.com";

        if (!userRepository.existsByEmail(adminEmail)) {

            Admin admin = new Admin();

            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("Admin123!"));
            admin.setRole(Role.ADMIN);


            userRepository.save(admin);
            System.out.println("Admin créé automatiquement.");
        } else {
            System.out.println("Admin déjà présent en base.");
        }
    }

}
