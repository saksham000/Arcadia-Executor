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
import com.online.school.school.exceptions.CreationException;
import com.online.school.school.exceptions.NotFoundException;

@Service
public class SubjectService {

    @Autowired
    private StudentRepo studentRepoService;

    @Autowired
    private SubjectRepo subjectRepoService;

    public Subject assignSubjectToStudent(Long stId, Subject subject) {
        Subject savedSubject = findSubjectById(subject.getSubjectId());
        Student student = studentRepoService.findById(stId)
                .orElseThrow(() -> new UsernameNotFoundException("Student with Id: " + stId + " Not Found"));
        student.getSubjects().add(savedSubject);
        studentRepoService.save(student);
        return savedSubject;
    }

    public Subject createSubject(Subject sub) {
        if (sub.getSubjectName() == null || sub.getSubjectName().trim().isEmpty()) {
            throw new CreationException("class name cannot be null or empty");
        }
        Subject newSub = Subject.builder()
                .subjectName(sub.getSubjectName())
                .build();

        return subjectRepoService.save(newSub);
    }

    public Subject updateSubject(Subject reqSub) {
        Subject savedSub = findSubjectById(reqSub.getSubjectId());
        if (reqSub.getSubjectName() != null) {
            savedSub.setSubjectName(reqSub.getSubjectName());
        }
        return subjectRepoService.save(savedSub);
    }

    public void deleteSubById(Long id) {
        Subject savedSub = findSubjectById(id);
        subjectRepoService.delete(savedSub);
    }

    public Subject findSubjectById(Long id) {
        Subject subject = subjectRepoService.findById(id)
                .orElseThrow(() -> new NotFoundException("subject id not found"));
        return subject;
    }

    public List<Subject> listAssignedSubjectsToStudent(Long stID) {
        Student studentOptional = studentRepoService.findById(stID)
                .orElseThrow(() -> new UsernameNotFoundException("Student with Id: " + stID + " is not Present"));

        return studentOptional.getSubjects();

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
