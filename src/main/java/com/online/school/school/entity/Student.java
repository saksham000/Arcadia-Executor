package com.online.school.school.entity;

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
    private Long studentId;

    
    private String studentName;
    
    private Long assigendClassId;
    
    private String studentPassword;
    private Set<Role> roles;
    
    @ManyToMany
    @JoinTable(name = "student_subjects", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    @Builder.Default
    private List<Subject> subjects = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "class_id")
    private StClass assignedStClass;

    public Student(Long studentId, String studentName, Long rollNumber, StClass assignedStClass) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.subjects = new ArrayList<>();
        this.assignedStClass = assignedStClass;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getAssignedClassId() {
        return assigendClassId;
    }

    public void setAssignedClassId(Long assigendClassId) {
        this.assigendClassId = assigendClassId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

}
