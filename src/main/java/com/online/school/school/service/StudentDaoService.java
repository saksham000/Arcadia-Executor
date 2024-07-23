package com.online.school.school.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.online.school.school.databasefiles.Student;
import com.online.school.school.exceptions.StudentNotFoundException;

@Service
public class StudentDaoService {

    private static List<Student> students = new ArrayList<>();
    private static int studentId = 1;

    static {
        students.add(new Student(studentId++, "saksham", studentId++, null));
        students.add(new Student(studentId++, "saxam", studentId++, null));
    }

    public List<Student> listAllStudents() {
        return students;
    }

    public Student findStudentById(int stId) {
        Optional<Student> studentOptional = students.stream().filter(s -> s.getStudentId() == stId).findFirst();
        if (studentOptional.isPresent()) {
            return studentOptional.get();
        } else {
            throw new StudentNotFoundException("Student with Id: " + stId + " Not Found");
        }
    }

    public Student createNewStudent(Student student) {
        student.setStudentId(studentId++);
        student.setRollNumber(studentId++);
        students.add(student);
        return student;
    }

    public void deleteStudentById(int stId) {
        findStudentById(stId);
        students.remove(stId);
    }

}
