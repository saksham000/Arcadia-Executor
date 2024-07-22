package com.online.school.school.databasefiles;

import java.util.HashSet;
import java.util.Set;

public class Teacher {
    private int teacherId;
    private String teacherName;
    private Set<StClass> classes;

    // Default constructor
    public Teacher() {
        this.classes = new HashSet<>();
    }

    // Parameterized constructor
    public Teacher(int teacherId, String teacherName) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.classes = new HashSet<>();
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

    public Set<StClass> getClasses() {
        return classes;
    }

    public void setClasses(Set<StClass> classes) {
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
