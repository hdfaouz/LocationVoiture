package com.enaa.locatiovoitures.Repositories;

import com.enaa.locatiovoitures.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    boolean existsByEmail(String adminEmail);
}
