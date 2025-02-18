package com.online.school.school.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.school.school.customApiResponse.ApiResponse;
import com.online.school.school.entity.Admin;
import com.online.school.school.entity.Student;
import com.online.school.school.entity.Teacher;
import com.online.school.school.service.AdminService;
import com.online.school.school.service.StudentService;
import com.online.school.school.service.TeacherService;

@RestController
@RequestMapping(path = "/public")
public class PublicController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @PostMapping(path = "/login-admin")
    public ResponseEntity<ApiResponse<String>> loginAdmin(@RequestBody Admin admin) {
        try {
            String token = adminService.loginAdmin(admin);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Login successful", token),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "Login failed: " + e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

    @PostMapping(path = "/login-teacher")
    public ResponseEntity<ApiResponse<String>> loginTeacher(@RequestBody Teacher reqTeacher) {
        try {
            String token = teacherService.loginTeacher(reqTeacher);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Login successful", token),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "Login failed: " + e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

    @PostMapping(path = "/login-student")
    public ResponseEntity<ApiResponse<String>> loginStudent(@RequestBody Student reqStudent) {
        try {
            String token = studentService.loginStudent(reqStudent);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Login successful", token),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "Login failed: " + e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

}
