package com.online.school.school.databasefiles;

import java.util.List;
import java.util.ArrayList;

public class Teacher {
    private int teacherId;
    private String teacherName;
    private List<StClass> classes;

    // Default constructor
    public Teacher() {
        this.classes = new ArrayList<>();
    }

    // Parameterized constructor
    public Teacher(int teacherId, String teacherName) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.classes = new ArrayList<>();
    }

    // Getters and Setters
    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public List<StClass> getClasses() {
        return classes;
    }

    public void setClasses(List<StClass> classes) {
        this.classes = classes;
    }

    // Utility method to add a class to the teacher's set of classes
    public void addClass(StClass stClass) {
        this.classes.add(stClass);
    }

    // toString method
    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", classes=" + classes +
                '}';
    }
}
