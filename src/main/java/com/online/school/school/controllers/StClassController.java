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
import com.online.school.school.entity.StClass;
import com.online.school.school.service.StClassService;
import com.online.school.school.service.StudentService;

@RestController
@RequestMapping(path = "/class")
public class StClassController {

    @Autowired
    private StudentService studentJpaDaoService;

    @Autowired
    private StClassService stClassJpaDaoService;

    @GetMapping(path = "/all-class")
    public ResponseEntity<ApiResponse<List<StClass>>> fetchAllClasses() {
        try {
            List<StClass> classes = stClassJpaDaoService.listAllClasses();
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.OK.value(), "Classes retrieved successfully", classes), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to fetch classes: " + e.getMessage(), null), HttpStatus.OK);
        }
    }

    @GetMapping(path = "/find-classid/{cId}")
    public ResponseEntity<ApiResponse<StClass>> fetchClassById(@PathVariable Long cId) {
        try {
            StClass stClass = stClassJpaDaoService.findClassById(cId);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Class found", stClass),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Class not found: " + e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

    @PostMapping(path = "/create-class")
    public ResponseEntity<ApiResponse<StClass>> createNewClass(@RequestBody StClass newClass) {
        try {
            StClass savedClass = stClassJpaDaoService.createNewClass(newClass);
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.CREATED.value(), "Class created successfully", savedClass),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error creating class: " + e.getMessage(), null), HttpStatus.OK);
        }
    }

    @PostMapping(path = "/update-class")
    public ResponseEntity<ApiResponse<StClass>> updateClass(@RequestBody StClass reqClass) {
        try {
            StClass updatedClass = stClassJpaDaoService.updateClass(reqClass);
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.OK.value(), "Class updated successfully", updatedClass),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error updating class: " + e.getMessage(), null), HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "/delete-classid/{cId}")
    public ResponseEntity<ApiResponse<Void>> deleteClassById(@PathVariable Long cId) {
        try {
            stClassJpaDaoService.deleteClassById(cId);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Class deleted successfully", null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Error deleting class: " + e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

    @GetMapping(path = "/assigne-class/{classId}/student/{stId}")
    public ResponseEntity<ApiResponse<Void>> assigneClassToStudents(@PathVariable Long stId,
            @PathVariable Long classId) {
        try {
            studentJpaDaoService.assigneClassToStudent(stId, classId);
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.OK.value(), "Class assigned to student successfully", null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Error assigning class: " + e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

}
