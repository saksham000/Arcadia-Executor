package com.online.school.school.databasefiles.jpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.school.school.databasefiles.Student;

public interface StudentRepo extends JpaRepository<Student, Integer> {
    
}
