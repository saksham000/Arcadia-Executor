package com.online.school.school.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.school.school.customApiResponse.ApiResponse;
import com.online.school.school.entity.Subject;
import com.online.school.school.service.SubjectService;

@RestController
@RequestMapping(path = "/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectJpaDaoService;

    @PostMapping(path = "/assign/subject/{stId}")
    public ResponseEntity<ApiResponse<Subject>> assignSubjectToStudent(@PathVariable Long stId,
            @RequestBody Subject subject) {
        try {
            Subject assignedSubject = subjectJpaDaoService.assignSubjectToStudent(stId, subject);
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.OK.value(), "Subject assigned successfully", assignedSubject),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Error assigning subject: " + e.getMessage(), null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/create-subject")
    public ResponseEntity<ApiResponse<Subject>> createNewSubject(@RequestBody Subject reqSubject) {
        try {
            Subject newSubject = subjectJpaDaoService.createSubject(reqSubject);
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.CREATED.value(), "Subject created successfully", newSubject),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error creating subject: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update-subject")
    public ResponseEntity<ApiResponse<Subject>> updateSubject(@RequestBody Subject reqSubject) {
        try {
            Subject updatedSubject = subjectJpaDaoService.updateSubject(reqSubject);
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.OK.value(), "Subject updated successfully", updatedSubject),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error updating subject: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/delete-subid/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSubject(@PathVariable Long id) {
        try {
            subjectJpaDaoService.deleteSubById(id);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Subject deleted successfully", null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Error deleting subject: " + e.getMessage(), null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/find-subid/{id}")
    public ResponseEntity<ApiResponse<Subject>> findSubjectById(@PathVariable Long id) {
        try {
            Subject subject = subjectJpaDaoService.findSubjectById(id);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Subject found", subject),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Subject not found: " + e.getMessage(), null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/assigned/subjects/{stId}")
    public ResponseEntity<ApiResponse<List<Subject>>> getAssignedSubjects(@PathVariable Long stId) {
        try {
            List<Subject> subjects = subjectJpaDaoService.listAssignedSubjectsToStudent(stId);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Subjects found", subjects),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Subjects not found: " + e.getMessage(), null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/assigned/subjects/{stId}/{subId}")
    public ResponseEntity<ApiResponse<Void>> deleteAssignedSubject(@PathVariable Long stId, @PathVariable Long subId) {
        try {
            subjectJpaDaoService.deleteSubjectOfStudentBySubId(stId, subId);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Subject deleted successfully", null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Subject not found: " + e.getMessage(), null),
                    HttpStatus.NOT_FOUND);
        }
    }

}
