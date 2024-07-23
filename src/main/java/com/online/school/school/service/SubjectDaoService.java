package com.online.school.school.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.school.school.databasefiles.Student;
import com.online.school.school.databasefiles.Subject;
import com.online.school.school.exceptions.SubjectNotFoundException;

@Service
public class SubjectDaoService {

    @Autowired
    private StudentDaoService studentDaoService;

    private static List<Subject> subjects = new ArrayList<>();
    private static int subjectId = 1;

    static {
        subjects.add(new Subject(subjectId++, "maths"));
        subjects.add(new Subject(subjectId++, "english"));
        subjects.add(new Subject(subjectId++, "hindi"));
    }

    public List<Subject> assigneSubjectsToStudent(int stId, List<Subject> subjects) {
        Student student = studentDaoService.findStudentById(stId);
        student.setSubjects(subjects);
        return subjects;
    }

    public List<Subject> listAssignedSubjectsToStudent(int stID) {
        Student student = studentDaoService.findStudentById(stID);
        return student.getSubjects();
    }

    public void deleteSubjectOfStudentBySubId(int stId, int subId) {
        Student student = studentDaoService.findStudentById(stId);
        List<Subject> subjects = student.getSubjects();

        Optional<Subject> subjecOptional = subjects.stream().filter(subject -> subject.getSubjectId() == subId)
                .findFirst();
        if (subjecOptional.isPresent()) {
            subjects.remove(subjecOptional.get());
        } else {
            throw new SubjectNotFoundException("Subject with Id: " + subId + " Not Found !");
        }
    }

}
