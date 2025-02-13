package com.online.school.school.databasefiles.jpaRepositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.school.school.databasefiles.Teacher;

public interface TeacherRepo extends JpaRepository<Teacher, Integer> {

    Optional<Teacher> findByTeacherName(String teacherUsername);
}
