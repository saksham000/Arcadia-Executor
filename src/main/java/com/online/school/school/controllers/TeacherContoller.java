package com.online.school.school.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.school.school.customApiResponse.ApiResponse;
import com.online.school.school.entity.Teacher;
import com.online.school.school.service.TeacherService;

@RestController
@RequestMapping(path = "/teacher")
public class TeacherContoller {

    @Autowired
    private TeacherService teacherJpaDaoService;

    @GetMapping(path = "/all-teacher")
    public ResponseEntity<ApiResponse<List<Teacher>>> fetchAllTeachers() {
        try {
            List<Teacher> teachers = teacherJpaDaoService.listAllTeachers();
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.OK.value(), "Teachers retrieved successfully", teachers),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.NOT_FOUND.value(),
                    "Failed to retrieve teachers: " + e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/assigneclass/{clasId}/teacher/{tId}")
    public ResponseEntity<ApiResponse<String>> assigneTeacherToClassByTid(@PathVariable Long clasId,
            @PathVariable Long tId) {
        try {
            teacherJpaDaoService.assigneTeacherToClass(clasId, tId);
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.OK.value(), "Teacher assigned to class successfully", null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Assignment failed: " + e.getMessage(), null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/create-teacher")
    public ResponseEntity<ApiResponse<Teacher>> createNewTeacher(@RequestBody Teacher teacher) {
        try {
            Teacher createdTeacher = teacherJpaDaoService.createNewTeacher(teacher);
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.CREATED.value(), "Teacher created successfully", createdTeacher),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error creating teacher: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/update-teacher")
    public ResponseEntity<ApiResponse<Teacher>> updateTeacher(@RequestBody Teacher reqTeacher) {
        try {
            Teacher updatedTeacher = teacherJpaDaoService.updateTeacher(reqTeacher);
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.OK.value(), "Teacher updated successfully", updatedTeacher),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error updating teacher: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/delete-teacher")
    public ResponseEntity<ApiResponse<String>> deleteTeacher() {
        try {
            teacherJpaDaoService.deleteTeacherById();
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Teacher deleted successfully", null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Error deleting teacher: " + e.getMessage(), null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/find-teacherid/{tId}")
    public ResponseEntity<ApiResponse<Teacher>> fetchTeacherById(@PathVariable Long tId) {
        try {
            Teacher teacher = teacherJpaDaoService.findTeacherById(tId);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Teacher found", teacher),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Teacher not found: " + e.getMessage(), null),
                    HttpStatus.NOT_FOUND);
        }
    }

}
