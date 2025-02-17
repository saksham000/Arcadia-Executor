package com.online.school.school.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.online.school.school.entity.StClass;
import com.online.school.school.service.StClassService;
import com.online.school.school.service.StudentService;

@RestController
@RequestMapping(path = "/class")
public class StClassJpaController {

    @Autowired
    private StudentService studentJpaDaoService;

    @Autowired
    private StClassService stClassJpaDaoService;

    @GetMapping(path = "/all-class")
    public List<StClass> fetchAllClasses() {
        return stClassJpaDaoService.listAllClasses();
    }

    @GetMapping(path = "/find-classid/{cId}")
    public StClass fetchClassById(@PathVariable Long cId) {
        try {
            return stClassJpaDaoService.findClassById(cId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "/create-class")
    public StClass createNewClass(@RequestBody StClass newclass) {
        stClassJpaDaoService.createNewClass(newclass);
        return newclass;
    }

    @DeleteMapping(path = "/delete-classid/{cId}")
    public void deleteClassById(@PathVariable Long cId) {
        try {
            stClassJpaDaoService.deleteClassById(cId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(path = "/assigne-class/{classId}/student/{stId}")
    public void assigneClassToStudents(@PathVariable Long stId, @PathVariable Long classId) {

        try {
            studentJpaDaoService.assigneClassToStudent(stId, classId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
