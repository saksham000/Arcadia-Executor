package com.online.school.school.databasefiles;

import java.util.Set;
import java.util.HashSet;

public class StClass {

    private int classId;
    private String className;
    private int seats;
    private Set<Student> students;
    private Teacher teacher;

    // Default constructor
    public StClass() {
        this.students = new HashSet<>();
    }

    // Parameterized constructor
    public StClass(int classId, String className, int seats, Teacher teacher) {
        this.classId = classId;
        this.className = className;
        this.seats = seats;
        this.students = new HashSet<>();
        this.teacher = teacher;
    }

    // Getters and Setters
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

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    // Utility method to add a student to the class
    public void addStudent(Student student) {
        this.students.add(student);
    }

}
