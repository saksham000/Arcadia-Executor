package com.online.school.school.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.online.school.school.entity.StClass;
import com.online.school.school.entity.Student;
import com.online.school.school.entity.jpaRepositories.StClassRepo;
import com.online.school.school.entity.jpaRepositories.StudentRepo;
import com.online.school.school.exceptions.NotFoundException;
import com.online.school.school.exceptions.UserAlredyPresentException;

@Service
public class StudentService {

    @Autowired
    private StClassRepo stClassRepoService;

    @Autowired
    private StudentRepo studentRepoService;

    public List<Student> listAllStudents() {
        return studentRepoService.findAll();
    }

    public Student findStudentById(Long stId) {
        Student studentOptional = studentRepoService.findById(stId)
                .orElseThrow(() -> new UsernameNotFoundException("Student with Id: " + stId + " Not Found !"));

        return studentOptional;
    }

    public Student createNewStudent(Student student) {
        studentRepoService.save(student);
        return student;
    }

    public void assigneClassToStudent(Long stId, Long classId) {

        StClass classOptional = stClassRepoService.findById(classId)
                .orElseThrow(() -> new NotFoundException("class Id not found"));
        Student studentOptional = studentRepoService.findById(stId)
                .orElseThrow(() -> new NotFoundException("Student id not found"));

        if (classOptional.getStudents().stream().anyMatch(s -> s.getStudentId() == stId)) {
            throw new UserAlredyPresentException("Student with Id: " + stId + " is Already Present in class");
        } else {
            studentOptional.setAssignedStClass(classOptional);
            classOptional.addStudent(studentOptional);
            studentOptional.setAssignedClassId(classOptional.getClassId());
            studentRepoService.save(studentOptional);
            stClassRepoService.save(classOptional);
        }

    }

    public void deleteStudentById(Long stId) {
        Student student = findStudentById(stId);
        studentRepoService.deleteById(student.getStudentId());
    }

}
