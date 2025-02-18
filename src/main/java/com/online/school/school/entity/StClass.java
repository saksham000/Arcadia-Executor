package com.online.school.school.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class StClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classId;

    private String className;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "assignedStClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students;

    public StClass(Long classId, String className, Teacher teacher) {
        this.classId = classId;
        this.className = className;
        this.students = new ArrayList<>();
        this.teacher = teacher;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

}
