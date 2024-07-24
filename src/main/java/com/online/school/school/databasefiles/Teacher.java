package com.online.school.school.databasefiles;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;

@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teacherId;
    
    private String teacherName;

    private int assignedClassId = 0;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StClass> classes;

    public Teacher() {
    }

    public Teacher(int teacherId, String teacherName) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.classes = new ArrayList<>();
    }

    public int getTeacherId() {
        return teacherId;
    }

    public int getAssignedClassId() {
        return assignedClassId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public void setAssignedClassId(int assigendClassId) {
        this.assignedClassId = assigendClassId;
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

    public void addClass(StClass stClass) {
        this.classes.add(stClass);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", classes=" + classes +
                '}';
    }
}
