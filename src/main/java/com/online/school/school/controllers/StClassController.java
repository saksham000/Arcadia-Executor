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

import com.online.school.school.databasefiles.StClass;
import com.online.school.school.exceptions.ClassNotFoundException;
import com.online.school.school.exceptions.StudentAlredyPresentException;
import com.online.school.school.exceptions.StudentNotFoundException;
import com.online.school.school.service.StClassDaoService;
import com.online.school.school.service.StudentDaoService;

@RestController
public class StClassController {
    @Autowired
    private StClassDaoService stClassDaoService;

    @Autowired
    private StudentDaoService studentDaoService;

    @GetMapping(path = "classes")
    public List<StClass> fetchAllClasses() {
        return stClassDaoService.listAllClasses();
    }

    @GetMapping(path = "classes/{cId}")
    public StClass fetchClassById(@PathVariable int cId) {
        try{
            return stClassDaoService.findClassById(cId);
        }catch(ClassNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "classes")
    public StClass createNewClass(@RequestBody StClass newclass){
        stClassDaoService.createNewClass(newclass);
        return newclass;
    }

    @DeleteMapping(path = "classes/{cId}")
    public void deleteClassById(@PathVariable int cId) {
        try{
            stClassDaoService.deleteClassById(cId);
        }catch(ClassNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(path = "classes/assigne/{classId}/student/{stId}")
    public void assigneClassToStudents(@PathVariable int stId, @PathVariable int classId) {

        try {
            studentDaoService.assigneClassToStudent(stId, classId);
        } catch (StudentNotFoundException | StudentAlredyPresentException | ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
