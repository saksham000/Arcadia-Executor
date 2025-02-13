package com.online.school.school.databasefiles;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;
    private Set<Role> roles;
    private String studentName;
    private int rollNumber;
    @Builder.Default
    private int assigendClassId = 0;
    private String studentPassword;
    // @JsonIgnore
    @ManyToMany
    @JoinTable(name = "student_subjects", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    @Builder.Default
    private List<Subject> subjects = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "class_id")
    private StClass assignedStClass;

  

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
