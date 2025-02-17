package com.online.school.school.controllers.jpaController;

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

import com.online.school.school.databasefiles.Teacher;
import com.online.school.school.service.jpaDaoService.TeacherJpaDaoService;

@RestController
@RequestMapping(path = "/teacher")
public class TeacherJpaContoller {

    @Autowired
    private TeacherJpaDaoService teacherJpaDaoService;

    @GetMapping(path = "teachers")
    public List<Teacher> fetchAllTeachers() {
        return teacherJpaDaoService.listAllTeachers();
    }

    @GetMapping(path = "teachers/assigneclass/{clasId}/teacher/{tId}")
    public void assigneTeacherToClassByTid(@PathVariable int clasId, @PathVariable int tId) {
        try {
            teacherJpaDaoService.assigneTeacherToClass(clasId, tId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "teachers")
    public Teacher createNewTeacher(@RequestBody Teacher teacher) {
        teacherJpaDaoService.createNewTeacher(teacher);
        return teacher;
    }

    @DeleteMapping(path = "teachers/{tId}")
    public void deleteTeacherById(@PathVariable int tId) {
        teacherJpaDaoService.deleteTeacherById(tId);
    }

    @GetMapping(path = "teachers/{tId}")
    public Teacher fetchTeacherById(@PathVariable int tId) {
        return teacherJpaDaoService.findTeacherById(tId);
    }
}
