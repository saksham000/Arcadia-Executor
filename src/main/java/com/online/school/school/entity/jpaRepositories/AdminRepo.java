package com.online.school.school.entity.jpaRepositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.school.school.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long> {

    Optional<Admin> findByAdminName(String adminName);
}
