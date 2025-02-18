package com.online.school.school.entity;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherId;

    private String teacherName;

    private Long assignedClassId;
    
    private String teacherPassword;

    private Set<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StClass> classes;

    public Teacher(Long teacherId, String teacherName) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.classes = new ArrayList<>();
    }

    public void setClasses(List<StClass> classes) {
        this.classes = classes;
    }

    public void addClass(StClass stClass) {
        this.classes.add(stClass);
    }
}
