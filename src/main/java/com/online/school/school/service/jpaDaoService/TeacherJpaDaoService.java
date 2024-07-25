package com.online.school.school.service.jpaDaoService;

import java.util.Optional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.school.school.databasefiles.StClass;
import com.online.school.school.databasefiles.Teacher;
import com.online.school.school.databasefiles.jpaRepositories.StClassRepo;
import com.online.school.school.databasefiles.jpaRepositories.TeacherRepo;
import com.online.school.school.exceptions.TeacherNotFoundException;

@Service
public class TeacherJpaDaoService {

    @Autowired
    private TeacherRepo teacherRepoService;

    @Autowired
    private StClassRepo stClassRepoService;

    public List<Teacher> listAllTeachers() {
        return teacherRepoService.findAll();
    }

    public Teacher findTeacherById(int tId) {
        Optional<Teacher> teacherOptional = teacherRepoService.findById(tId);
        if (teacherOptional.isPresent()) {
            return teacherOptional.get();
        } else {
            throw new TeacherNotFoundException("Teacher with Id: " + tId + " is not Found !");
        }
    }

    public void assigneTeacherToClass(int classId, int tId) {
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
        teacher.setAssignedClassId(0);
        teacherRepoService.save(teacher);
        return teacher;
    }

    public void deleteTeacherById(int tId) {
        Teacher teacher = findTeacherById(tId);
        teacherRepoService.deleteById(teacher.getTeacherId());
    }

}
