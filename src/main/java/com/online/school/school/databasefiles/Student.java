package com.online.school.school.databasefiles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Student {


    private int studentId;
    private String studentName;
    private int rollNumber;

    @JsonIgnore
    private List<Subject> subjects = new ArrayList<>();

    @JsonIgnore
    private StClass assignedStClass;

    private int assigendClassId = 0;

    // Default constructor
    public Student() {
    }

    // Parameterized constructor
    public Student(int studentId, String studentName, int rollNumber, StClass assignedStClass) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.rollNumber = rollNumber;
        this.subjects = new ArrayList<>();
        this.assignedStClass = assignedStClass;
    }

    // Getters and Setters
    public int getStudentId() {
        return studentId;
    }

    public int getAssignedClassId(){
        return assigendClassId;
    }

    public void setAssignedClassId(int assigendClassId){
        this.assigendClassId = assigendClassId;
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

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
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
