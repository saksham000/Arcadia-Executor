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

import com.online.school.school.entity.Teacher;
import com.online.school.school.service.TeacherService;

@RestController
@RequestMapping(path = "/teacher")
public class TeacherJpaContoller {

    @Autowired
    private TeacherService teacherJpaDaoService;

    @GetMapping(path = "all-teacher")
    public List<Teacher> fetchAllTeachers() {
        return teacherJpaDaoService.listAllTeachers();
    }

    @GetMapping(path = "assigneclass/{clasId}/teacher/{tId}")
    public void assigneTeacherToClassByTid(@PathVariable Long clasId, @PathVariable Long tId) {
        try {
            teacherJpaDaoService.assigneTeacherToClass(clasId, tId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "create-teacher")
    public Teacher createNewTeacher(@RequestBody Teacher teacher) {
        teacherJpaDaoService.createNewTeacher(teacher);
        return teacher;
    }

    @DeleteMapping(path = "teachers/{tId}")
    public void deleteTeacherById(@PathVariable Long tId) {
        teacherJpaDaoService.deleteTeacherById(tId);
    }

    @GetMapping(path = "find-teacherid/{tId}")
    public Teacher fetchTeacherById(@PathVariable Long tId) {
        return teacherJpaDaoService.findTeacherById(tId);
    }
}
