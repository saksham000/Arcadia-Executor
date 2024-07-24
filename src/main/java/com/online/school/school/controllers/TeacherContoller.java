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

import com.online.school.school.databasefiles.Teacher;
import com.online.school.school.exceptions.ClassNotFoundException;
import com.online.school.school.exceptions.TeacherNotFoundException;
import com.online.school.school.service.TeacherDaoService;

@RestController
public class TeacherContoller {

    @Autowired
    private TeacherDaoService teacherDaoService;

    @GetMapping(path = "teachers")
    public List<Teacher> fetchAllTeachers(){
        return teacherDaoService.listAllTeachers();
    }

    @GetMapping(path = "teachers/assigneclass/{clasId}/teacher/{tId}")
    public void assigneTeacherToClassByTid(@PathVariable int clasId,@PathVariable int tId){
        try{
            teacherDaoService.assigneTeacherToClass(clasId,tId);
        }catch(ClassNotFoundException | TeacherNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "teachers")
    public Teacher createNewTeacher(@RequestBody Teacher teacher){
        teacherDaoService.createNewTeacher(teacher);
        return teacher;
    }

    @DeleteMapping(path = "teachers/{tId}")
    public void deleteTeacherById(@PathVariable int tId){
        teacherDaoService.deleteTeacherById(tId);
    }

    @GetMapping(path = "teachers/{tId}")
    public Teacher fetchTeacherById(@PathVariable int tId){
        return teacherDaoService.findTeacherById(tId);
    }
}
