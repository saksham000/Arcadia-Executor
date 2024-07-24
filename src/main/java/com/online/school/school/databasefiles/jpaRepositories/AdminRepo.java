package com.online.school.school.databasefiles.jpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.school.school.databasefiles.Admin;

public interface AdminRepo extends JpaRepository<Admin, Integer> {
    
}
