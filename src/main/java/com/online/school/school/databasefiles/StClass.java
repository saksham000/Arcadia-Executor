package com.online.school.school.databasefiles;

import java.util.List;
import java.util.ArrayList;

public class StClass {

    private int classId;
    private String className;
    private Teacher teacher;
    private List<Student> students;

    public StClass() {
        this.students = new ArrayList<>();
    }

    public StClass(int classId, String className, Teacher teacher) {
        this.classId = classId;
        this.className = className;
        this.students = new ArrayList<>();
        this.teacher = teacher;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

}
