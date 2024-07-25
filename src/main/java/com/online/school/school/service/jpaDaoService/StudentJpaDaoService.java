package com.online.school.school.service.jpaDaoService;

import java.util.Optional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.school.school.databasefiles.StClass;
import com.online.school.school.databasefiles.Student;
import com.online.school.school.databasefiles.jpaRepositories.StClassRepo;
import com.online.school.school.databasefiles.jpaRepositories.StudentRepo;
import com.online.school.school.exceptions.ClassNotFoundException;
import com.online.school.school.exceptions.StudentAlredyPresentException;
import com.online.school.school.exceptions.StudentNotFoundException;

@Service
public class StudentJpaDaoService {
    private static int studentRollNo = 1;
    @Autowired
    private StClassRepo stClassRepoService;

    @Autowired
    private StudentRepo studentRepoService;

    public List<Student> listAllStudents() {
        return studentRepoService.findAll();
    }

    public Student findStudentById(int stId) {
        Optional<Student> studentOptional = studentRepoService.findById(stId);
        if (studentOptional.isPresent()) {
            return studentOptional.get();
        } else {
            throw new StudentNotFoundException("Student with Id: " + stId + " Not Found !");
        }

    }

    public Student createNewStudent(Student student) {
        student.setRollNumber(studentRollNo++);
        studentRepoService.save(student);
        return student;
    }

    public void assigneClassToStudent(int stId, int classId) {

        Optional<StClass> classOptional = stClassRepoService.findById(classId);
        Optional<Student> studentOptional = studentRepoService.findById(stId);

        if (!classOptional.isPresent()) {
            throw new ClassNotFoundException("Class with Id: " + classId + " Not Found!");
        }

        if (!studentOptional.isPresent()) {
            throw new StudentNotFoundException("Student with Id: " + stId + " Not Found!");
        }

        StClass assignedClass = classOptional.get();
        Student student = studentOptional.get();

        boolean isStudentAlreadyPresent = assignedClass.getStudents().stream().anyMatch(s -> s.getStudentId() == stId);

        if (isStudentAlreadyPresent) {
            throw new StudentAlredyPresentException("Student with Id: " + stId + " is Already Present in class");
        } else {
            student.setAssignedStClass(assignedClass);
            assignedClass.addStudent(student);
            student.setAssignedClassId(assignedClass.getClassId());
            studentRepoService.save(student);
            stClassRepoService.save(assignedClass);
        }

    }

    public void deleteStudentById(int stId) {
        Student student = findStudentById(stId);
        studentRepoService.deleteById(student.getStudentId());
    }

}
