package com.online.school.school.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.online.school.school.databasefiles.Student;
import com.online.school.school.exceptions.StudentNotFoundException;
import com.online.school.school.service.StudentDaoService;

@RestController
public class StudentController {

    @Autowired
    private StudentDaoService studentDaoService;

    @GetMapping(path = "students")
    public List<Student> fetchAllStudents() {
        return studentDaoService.listAllStudents();
    }

    @GetMapping(path = "students/{stId}")
    public Student findStudentbyId(@PathVariable int stId) {
        try{
            return studentDaoService.findStudentById(stId);
        }catch(StudentNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "students")
    public Student createStudent(@RequestBody Student student) {
        return studentDaoService.createNewStudent(student);
    }

    @DeleteMapping(path = "students/{stId}")
    public void deleteStudent(@PathVariable int stId) {
        try {
            studentDaoService.deleteStudentById(stId);
        } catch (StudentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
