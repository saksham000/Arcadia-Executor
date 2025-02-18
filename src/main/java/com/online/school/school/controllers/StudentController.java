package com.online.school.school.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.online.school.school.customApiResponse.ApiResponse;
import com.online.school.school.entity.Student;
import com.online.school.school.service.StudentService;

@RestController
@RequestMapping(path = "/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(path = "/all-student")
    public ResponseEntity<ApiResponse<List<Student>>> fetchAllStudents() {
        try {
            List<Student> students = studentService.listAllStudents();
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.OK.value(), "Students retrieved successfully", students),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error fetching students: " + e.getMessage(), null), HttpStatus.OK);
        }
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
    public ResponseEntity<ApiResponse<Student>> createStudent(@RequestBody Student student) {
        try {
            Student newStudent = studentService.createNewStudent(student);
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.CREATED.value(), "Student created successfully", newStudent),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error creating student: " + e.getMessage(), null), HttpStatus.OK);
        }
    }

    @PostMapping(path = "/update-student")
    public ResponseEntity<ApiResponse<Student>> updateStudent(@RequestBody Student reqStudent) {
        try {
            Student updatedStudent = studentService.updateStudent(reqStudent);
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.OK.value(), "Student updated successfully", updatedStudent),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error updating student: " + e.getMessage(), null), HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "/students")
    public ResponseEntity<ApiResponse<Void>> deleteStudent() {
        try {
            studentService.deleteStudentById();
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Student deleted successfully", null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Student not found: " + e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

}
