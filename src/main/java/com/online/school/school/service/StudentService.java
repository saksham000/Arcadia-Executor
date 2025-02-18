package com.online.school.school.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.online.school.school.entity.Admin;
import com.online.school.school.entity.Role;
import com.online.school.school.entity.StClass;
import com.online.school.school.entity.Student;
import com.online.school.school.entity.jpaRepositories.AdminRepo;
import com.online.school.school.entity.jpaRepositories.StClassRepo;
import com.online.school.school.entity.jpaRepositories.StudentRepo;
import com.online.school.school.entity.jpaRepositories.TeacherRepo;
import com.online.school.school.exceptions.CreationException;
import com.online.school.school.exceptions.NotFoundException;
import com.online.school.school.exceptions.UserAlredyPresentException;
import com.online.school.school.security.JwtUtils;
import com.online.school.school.security.UserDetailsServiceImpl;

@Service
public class StudentService {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private StClassRepo stClassRepoService;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private AdminRepo adminRepo;

    public String loginStudent(Student student) {

        Optional<Student> optionalStudent = studentRepo.findByStudentName(student.getStudentName());

        if (optionalStudent.isPresent()) {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            student.getStudentName(),
                            student.getStudentPassword()));

            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(student.getStudentName());
            return jwtUtils.generateToken(userDetails);
        } else {
            throw new UsernameNotFoundException("Incorrect username or password");
        }
    }

    public Student getAuthenticatedStudent() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();

        return studentRepo.findByStudentName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found!"));
    }

    public List<Student> listAllStudents() {
        return studentRepo.findAll();
    }

    public Student findStudentById(Long stId) {
        Student studentOptional = studentRepo.findById(stId)
                .orElseThrow(() -> new UsernameNotFoundException("Student with Id: " + stId + " Not Found !"));

        return studentOptional;
    }

    public Student createNewStudent(Student student) {

        if (student.getStudentName() == null || student.getStudentName().trim().isEmpty()
                || student.getStudentPassword() == null || student.getStudentPassword().trim().isEmpty()) {
            throw new CreationException("Admin or password cannot be null or empty");
        }

        Optional<Admin> existingAdminUserName = adminRepo.findByAdminName(student.getStudentName());
        Optional<?> existingTeacherName = teacherRepo.findByTeacherName(student.getStudentName());
        Optional<?> existingUserEntName = studentRepo.findByStudentName(student.getStudentName());

        if (existingAdminUserName.isPresent() || existingTeacherName.isPresent() || existingUserEntName.isPresent()) {
            throw new CreationException("Student username is already used");
        }

        Student newStudent = Student.builder()
                .studentName(student.getStudentName())
                .studentPassword(passwordEncoder.encode(student.getStudentPassword()))
                .assigendClassId(null)
                .assignedStClass(null)
                .subjects(null)
                .roles(Set.of(Role.STUDENT))
                .build();

        return studentRepo.save(newStudent);
    }

    public Student updateStudent(Student reqStudent) {
        Student authStudent = getAuthenticatedStudent();

        if (authStudent.getStudentId() == null || authStudent.getStudentId() != reqStudent.getStudentId()) {
            throw new IllegalArgumentException("Student id is null or you are not auth to update");
        }
        if (reqStudent.getStudentName() != null) {
            authStudent.setStudentName(reqStudent.getStudentName());
        }

        if (reqStudent.getStudentPassword() != null) {
            authStudent.setStudentPassword(passwordEncoder.encode(reqStudent.getStudentPassword()));
        }
        if (reqStudent.getAssigendClassId() != null) {
            authStudent.setAssigendClassId(reqStudent.getAssigendClassId());
        }

        if (reqStudent.getSubjects() != null) {
            authStudent.setSubjects(reqStudent.getSubjects());
        }

        if (reqStudent.getAssignedStClass() != null) {
            authStudent.setAssignedStClass(reqStudent.getAssignedStClass());
        }
        return studentRepo.save(authStudent);
    }

    public void assigneClassToStudent(Long stId, Long classId) {

        StClass classOptional = stClassRepoService.findById(classId)
                .orElseThrow(() -> new NotFoundException("class Id not found"));
        Student studentOptional = studentRepo.findById(stId)
                .orElseThrow(() -> new NotFoundException("Student id not found"));

        if (classOptional.getStudents().stream().anyMatch(s -> s.getStudentId() == stId)) {
            throw new UserAlredyPresentException("Student with Id: " + stId + " is Already Present in class");
        } else {
            studentOptional.setAssignedStClass(classOptional);
            classOptional.addStudent(studentOptional);
            studentOptional.setAssignedClassId(classOptional.getClassId());
            studentRepo.save(studentOptional);
            stClassRepoService.save(classOptional);
        }

    }

    public void deleteStudentById() {
        Student student = findStudentById(getAuthenticatedStudent().getStudentId());
        studentRepo.delete(student);
    }

}
