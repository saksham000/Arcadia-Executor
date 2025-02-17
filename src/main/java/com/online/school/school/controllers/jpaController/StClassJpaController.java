package com.online.school.school.controllers.jpaController;

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

import com.online.school.school.databasefiles.StClass;
import com.online.school.school.exceptions.ClassNotFoundException;
import com.online.school.school.service.jpaDaoService.StClassJpaDaoService;
import com.online.school.school.service.jpaDaoService.StudentJpaDaoService;

@RestController
public class StClassJpaController {

    @Autowired
    private StudentJpaDaoService studentJpaDaoService;

    @Autowired
    private StClassJpaDaoService stClassJpaDaoService;

    @GetMapping(path = "classes")
    public List<StClass> fetchAllClasses() {
        return stClassJpaDaoService.listAllClasses();
    }

    @GetMapping(path = "classes/{cId}")
    public StClass fetchClassById(@PathVariable int cId) {
        try {
            return stClassJpaDaoService.findClassById(cId);
        } catch (ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "classes")
    public StClass createNewClass(@RequestBody StClass newclass) {
        stClassJpaDaoService.createNewClass(newclass);
        return newclass;
    }

    @DeleteMapping(path = "classes/{cId}")
    public void deleteClassById(@PathVariable int cId) {
        try {
            stClassJpaDaoService.deleteClassById(cId);
        } catch (ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(path = "classes/assigne/{classId}/student/{stId}")
    public void assigneClassToStudents(@PathVariable int stId, @PathVariable int classId) {

        try {
            studentJpaDaoService.assigneClassToStudent(stId, classId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
