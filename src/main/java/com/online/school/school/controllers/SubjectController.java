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

import com.online.school.school.databasefiles.Subject;
import com.online.school.school.exceptions.StudentNotFoundException;
import com.online.school.school.exceptions.SubjectNotFoundException;
import com.online.school.school.service.SubjectDaoService;

@RestController
public class SubjectController {

    @Autowired
    private SubjectDaoService subjectDaoService;

    @PostMapping(path = "students/assigne/subjects/{stId}")
    public List<Subject> assigneSubjectstoStudent(@PathVariable int stId, @RequestBody List<Subject> subject) {
        try {
            return subjectDaoService.assigneSubjectsToStudent(stId, subject);
        } catch (StudentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(path = "students/assigned/subjects/{stId}")
    public List<Subject> getAssignedSubjects(@PathVariable int stId) {
        try {
            return subjectDaoService.listAssignedSubjectsToStudent(stId);
        } catch (StudentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping(path = "students/assigned/subjects/{stId}/{subId}")
    public void deleteAssignedSubject(@PathVariable int stId, @PathVariable int subId) {
        try {
            subjectDaoService.deleteSubjectOfStudentBySubId(stId, subId);
        } catch (SubjectNotFoundException | StudentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
