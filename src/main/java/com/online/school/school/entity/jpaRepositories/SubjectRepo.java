package com.online.school.school.entity.jpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.school.school.entity.Subject;

public interface SubjectRepo extends JpaRepository<Subject, Long> {
    
}
