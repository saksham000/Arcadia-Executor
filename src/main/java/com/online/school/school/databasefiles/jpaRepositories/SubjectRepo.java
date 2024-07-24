package com.online.school.school.databasefiles.jpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.school.school.databasefiles.Subject;

public interface SubjectRepo extends JpaRepository<Subject, Integer> {
    
}
