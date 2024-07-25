package com.online.school.school.databasefiles;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.ArrayList;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    private String studentName;
    private int rollNumber;
    private int assigendClassId = 0;

    // @JsonIgnore
    @ManyToMany
    @JoinTable(name = "student_subjects", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Subject> subjects = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "class_id")
    private StClass assignedStClass;

    public Student() {
    }

    public Student(int studentId, String studentName, int rollNumber, StClass assignedStClass) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.rollNumber = rollNumber;
        this.subjects = new ArrayList<>();
        this.assignedStClass = assignedStClass;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getAssignedClassId() {
        return assigendClassId;
    }

    public void setAssignedClassId(int assigendClassId) {
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

    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }

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
