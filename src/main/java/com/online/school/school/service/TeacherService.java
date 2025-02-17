package com.online.school.school.service;

import java.util.Optional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.online.school.school.entity.StClass;
import com.online.school.school.entity.Teacher;
import com.online.school.school.entity.jpaRepositories.StClassRepo;
import com.online.school.school.entity.jpaRepositories.TeacherRepo;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepo teacherRepoService;

    @Autowired
    private StClassRepo stClassRepoService;

    public List<Teacher> listAllTeachers() {
        return teacherRepoService.findAll();
    }

    public Teacher findTeacherById(Long tId) {
        Optional<Teacher> teacherOptional = teacherRepoService.findById(tId);
        if (teacherOptional.isPresent()) {
            return teacherOptional.get();
        } else {
            throw new UsernameNotFoundException("Teacher with Id: " + tId + " is not Found !");
        }
    }

    public void assigneTeacherToClass(Long classId, Long tId) {
        Optional<StClass> classOptional = stClassRepoService.findById(classId);
        StClass assigneClass = classOptional.get();
        if (classOptional.isPresent()) {
            Teacher teacher = findTeacherById(tId);
            teacher.addClass(assigneClass);
            assigneClass.setTeacher(teacher);
            teacher.setAssignedClassId(assigneClass.getClassId());
            stClassRepoService.save(assigneClass);
            teacherRepoService.save(teacher);
        }

    }

    public Teacher createNewTeacher(Teacher teacher) {
        teacher.setClasses(null);
        teacher.setAssignedClassId(null);
        teacherRepoService.save(teacher);
        return teacher;
    }

    public void deleteTeacherById(Long tId) {
        Teacher teacher = findTeacherById(tId);
        teacherRepoService.deleteById(teacher.getTeacherId());
    }

}
