package com.online.school.school.databasefiles.jpaRepositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.school.school.databasefiles.Admin;

public interface AdminRepo extends JpaRepository<Admin, Integer> {
    // Optional<Admin> findByAdminEmail(String adminEmail);
    Optional<Admin> findByAdminName(String adminName);
}
