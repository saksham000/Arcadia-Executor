package com.online.school.school.controllers.jpaController;

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

import com.online.school.school.databasefiles.Student;
import com.online.school.school.service.jpaDaoService.StudentJpaDaoService;

@RestController
@RequestMapping(path = "/student")
public class StudentJpaController {

    @Autowired
    private StudentJpaDaoService studentJpaDaoService;

    @GetMapping(path = "students")
    public List<Student> fetchAllStudents() {
        return studentJpaDaoService.listAllStudents();
    }

    @GetMapping(path = "students/{stId}")
    public Student findStudentbyId(@PathVariable int stId) {
        try {
            return studentJpaDaoService.findStudentById(stId);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "students")
    public Student createStudent(@RequestBody Student student) {
        return studentJpaDaoService.createNewStudent(student);
    }

    @DeleteMapping(path = "students/{stId}")
    public void deleteStudent(@PathVariable int stId) {
        try {
            studentJpaDaoService.deleteStudentById(stId);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
