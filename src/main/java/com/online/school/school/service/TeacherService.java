package com.online.school.school.service;

import java.util.Optional;
import java.util.Set;
import java.util.List;
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
import com.online.school.school.entity.Teacher;
import com.online.school.school.entity.jpaRepositories.AdminRepo;
import com.online.school.school.entity.jpaRepositories.StClassRepo;
import com.online.school.school.entity.jpaRepositories.StudentRepo;
import com.online.school.school.entity.jpaRepositories.TeacherRepo;
import com.online.school.school.exceptions.CreationException;
import com.online.school.school.security.JwtUtils;
import com.online.school.school.security.UserDetailsServiceImpl;

@Service
public class TeacherService {
    @Autowired
    private StClassRepo stClassRepoService;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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

    public String loginTeacher(Teacher teacher) {

        Optional<Teacher> optionalTeacher = teacherRepo.findByTeacherName(teacher.getTeacherName());

        if (optionalTeacher.isPresent()) {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            teacher.getTeacherName(),
                            teacher.getTeacherPassword()));

            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(teacher.getTeacherName());
            return jwtUtils.generateToken(userDetails);
        } else {
            throw new UsernameNotFoundException("Incorrect username or password");
        }
    }

    public Teacher getAuthenticatedTeacher() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();

        return teacherRepo.findByTeacherName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Teacher is not found!"));
    }

    public List<Teacher> listAllTeachers() {
        return teacherRepo.findAll();
    }

    public Teacher findTeacherById(Long tId) {
        Teacher teacherOptional = teacherRepo.findById(tId)
                .orElseThrow(() -> new UsernameNotFoundException("Teacher with Id: " + tId + " is not Found !"));
        return teacherOptional;
    }

    public void assigneTeacherToClass(Long classId, Long tId) {
        Optional<StClass> classOptional = stClassRepoService.findById(classId);
        StClass assigneClass = classOptional.get();
        if (classOptional.isPresent()) {
            Teacher teacher = findTeacherById(tId);
            teacher.addClass(assigneClass);
            assigneClass.setTeacher(teacher);
            teacher.setAssignedClassId(assigneClass.getClassId());
            stClassRepoService.save(assigneClass);
            teacherRepo.save(teacher);
        }

    }

    public Teacher createNewTeacher(Teacher teacher) {

        if (teacher.getTeacherName() == null || teacher.getTeacherName().trim().isEmpty()
                || teacher.getTeacherPassword() == null || teacher.getTeacherPassword().trim().isEmpty()) {
            throw new CreationException("Teacher or password cannot be null or empty");
        }

        Optional<Admin> existingAdminUserName = adminRepo.findByAdminName(teacher.getTeacherName());
        Optional<?> existingTeacherName = teacherRepo.findByTeacherName(teacher.getTeacherName());
        Optional<?> existingStudentName = studentRepo.findByStudentName(teacher.getTeacherName());

        if (existingAdminUserName.isPresent() || existingTeacherName.isPresent() || existingStudentName.isPresent()) {
            throw new CreationException("Teacher username is already used");
        }

        Teacher newTeacher = Teacher.builder()
                .teacherName(teacher.getTeacherName())
                .teacherPassword(passwordEncoder.encode(teacher.getTeacherPassword()))
                .assignedClassId(null)
                .classes(null)
                .roles(Set.of(Role.TEACHER))
                .build();

        return teacherRepo.save(newTeacher);
    }

    public Teacher updateTeacher(Teacher reqTeacher) {
        Teacher authTeacher = getAuthenticatedTeacher();

        if (reqTeacher.getTeacherId() != null || reqTeacher.getTeacherId() != authTeacher.getTeacherId()) {
            throw new IllegalArgumentException("Teacher id is null or you are not auth to update");
        }

        if (reqTeacher.getTeacherName() != null) {
            authTeacher.setTeacherName(reqTeacher.getTeacherName());
        }
        if (reqTeacher.getTeacherPassword() != null) {
            authTeacher.setTeacherPassword(passwordEncoder.encode(reqTeacher.getTeacherPassword()));
        }
        if (reqTeacher.getAssignedClassId() != null) {
            authTeacher.setAssignedClassId(reqTeacher.getAssignedClassId());
        }
        if (reqTeacher.getClass() != null) {
            authTeacher.setClasses(reqTeacher.getClasses());
        }
        return teacherRepo.save(authTeacher);
    }

    public void deleteTeacherById() {
        Teacher teacher = findTeacherById(getAuthenticatedTeacher().getTeacherId());
        teacherRepo.delete(teacher);
    }

}
