package com.online.school.school.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.online.school.school.entity.Student;
import com.online.school.school.service.StudentService;

@RestController
@RequestMapping(path = "/student")
public class StudentJpaController {

    @Autowired
    private StudentService studentService;

    @GetMapping(path = "/all-student")
    public List<Student> fetchAllStudents() {
        return studentService.listAllStudents();
    }

    @GetMapping(path = "/find-studentid/{stId}")
    public Student findStudentbyId(@PathVariable Long stId) {
        try {
            return studentService.findStudentById(stId);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "/create-student")
    public Student createStudent(@RequestBody Student student) {
        return studentService.createNewStudent(student);
    }

    @DeleteMapping(path = "/students/{stId}")
    public void deleteStudent(@PathVariable Long stId) {
        try {
            studentService.deleteStudentById(stId);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
