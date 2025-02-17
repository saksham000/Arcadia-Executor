package com.online.school.school.entity.jpaRepositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.school.school.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentName(String studentName);

}
