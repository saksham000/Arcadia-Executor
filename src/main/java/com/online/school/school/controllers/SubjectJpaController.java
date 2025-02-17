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

import com.online.school.school.entity.Subject;
import com.online.school.school.service.SubjectService;

@RestController
@RequestMapping(path = "/student")
public class SubjectJpaController {

    @Autowired
    private SubjectService subjectJpaDaoService;

    @PostMapping(path = "students/assigne/subject/{stId}")
    public Subject assigneSubjectstoStudent(@PathVariable Long stId, @RequestBody Subject subject) {
        try {
            return subjectJpaDaoService.assignSubjectToStudent(stId, subject);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(path = "students/assigned/subjects/{stId}")
    public List<Subject> getAssignedSubjects(@PathVariable Long stId) {
        try {
            return subjectJpaDaoService.listAssignedSubjectsToStudent(stId);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping(path = "students/assigned/subjects/{stId}/{subId}")
    public void deleteAssignedSubject(@PathVariable Long stId, @PathVariable Long subId) {
        try {
            subjectJpaDaoService.deleteSubjectOfStudentBySubId(stId, subId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
