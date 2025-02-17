package com.online.school.school.entity.jpaRepositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.school.school.entity.Teacher;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByTeacherName(String teacherUsername);
}
