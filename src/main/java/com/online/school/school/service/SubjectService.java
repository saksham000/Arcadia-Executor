package com.online.school.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.online.school.school.entity.Student;
import com.online.school.school.entity.Subject;
import com.online.school.school.entity.jpaRepositories.StudentRepo;
import com.online.school.school.entity.jpaRepositories.SubjectRepo;

@Service
public class SubjectService {

    @Autowired
    private StudentRepo studentRepoService;

    @Autowired
    private SubjectRepo subjectRepoService;

    public Subject assignSubjectToStudent(Long stId, Subject subject) {
        Subject savedSubject = subjectRepoService.save(subject);
        Student student = studentRepoService.findById(stId)
                .orElseThrow(() -> new UsernameNotFoundException("Student with Id: " + stId + " Not Found"));
        student.getSubjects().add(savedSubject);
        studentRepoService.save(student);
        return savedSubject;
    }

    public List<Subject> listAssignedSubjectsToStudent(Long stID) {
        Optional<Student> studentOptional = studentRepoService.findById(stID);
        if (!studentOptional.isPresent()) {
            throw new UsernameNotFoundException("Student with Id: " + stID + " is not Present");
        }
        return studentOptional.get().getSubjects();

    }

    public void deleteSubjectOfStudentBySubId(Long stId, Long subId) {
        Student student = studentRepoService.findById(stId)
                .orElseThrow(() -> new UsernameNotFoundException("Student with Id: " + stId + " Not Found"));

        List<Subject> subjects = student.getSubjects();

        Optional<Subject> subjectOptional = subjects.stream().filter(subject -> subject.getSubjectId() == subId)
                .findFirst();

        if (subjectOptional.isPresent()) {
            subjects.remove(subjectOptional.get());

            student.setSubjects(subjects);

            studentRepoService.save(student);
        } else {
            throw new IllegalArgumentException("Subject with Id: " + subId + " Not Found!");
        }
    }

}
