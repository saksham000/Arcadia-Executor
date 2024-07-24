package com.online.school.school.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.school.school.databasefiles.StClass;
import com.online.school.school.databasefiles.Teacher;
import com.online.school.school.exceptions.TeacherNotFoundException;

@Service
public class TeacherDaoService {

    @Autowired
    private StClassDaoService stClassDaoService;

    private static List<Teacher> teachers = new ArrayList<>();

    private static int teacherId = 1;

    static {
        teachers.add(new Teacher(teacherId++, "saksham"));
        teachers.add(new Teacher(teacherId++, "saxam"));

    }

    public List<Teacher> listAllTeachers() {
        return teachers;
    }

    public Teacher findTeacherById(int tId) {
        Optional<Teacher> teacherOptional = teachers.stream().filter(t -> t.getTeacherId() == tId).findFirst();
        if (teacherOptional.isPresent()) {
            return teacherOptional.get();
        } else {
            throw new TeacherNotFoundException("Teacher with Id: " + tId + " is not Found !");
        }
    }

    public void assigneTeacherToClass(int classId, int tId) {
        StClass assigneClass = stClassDaoService.findClassById(classId);
        Teacher teacher = findTeacherById(tId);
        teacher.addClass(assigneClass);
        assigneClass.setTeacher(teacher);
        teacher.setAssignedClassId(assigneClass.getClassId());
    }

    public Teacher createNewTeacher(Teacher teacher) {
        teacher.setTeacherId(teacherId++);
        teacher.setClasses(null);
        teacher.setAssignedClassId(0);
        teachers.add(teacher);
        return teacher;
    }

    public void deleteTeacherById(int tId) {
        findTeacherById(tId);
        teachers.removeIf(t -> t.getTeacherId() == tId);
    }

}
