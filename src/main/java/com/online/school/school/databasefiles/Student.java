package com.online.school.school.databasefiles;

import java.util.Set;
import java.util.HashSet;

public class Student {
    private int studentId;
    private String studentName;
    private int rollNumber;
    private Set<Subject> subjects;
    private StClass assignedStClass;

    // Default constructor
    public Student() {
        this.subjects = new HashSet<>();
    }

    // Parameterized constructor
    public Student(int studentId, String studentName, int rollNumber, StClass assignedStClass) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.rollNumber = rollNumber;
        this.subjects = new HashSet<>();
        this.assignedStClass = assignedStClass;
    }

    // Getters and Setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public StClass getAssignedStClass() {
        return assignedStClass;
    }

    public void setAssignedStClass(StClass assignedStClass) {
        this.assignedStClass = assignedStClass;
    }

    // Utility method to add a subject to the student
    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }

    // toString method
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", rollNumber=" + rollNumber +
                ", subjects=" + subjects +
                ", assignedStClass=" + assignedStClass +
                '}';
    }
}
