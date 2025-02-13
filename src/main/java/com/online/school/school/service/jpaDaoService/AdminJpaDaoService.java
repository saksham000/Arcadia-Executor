package com.online.school.school.service.jpaDaoService;

import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.online.school.school.databasefiles.Admin;
import com.online.school.school.databasefiles.Role;
import com.online.school.school.databasefiles.jpaRepositories.AdminRepo;
import com.online.school.school.databasefiles.jpaRepositories.StudentRepo;
import com.online.school.school.databasefiles.jpaRepositories.TeacherRepo;
import com.online.school.school.exceptions.AdminNotFoundException;
import com.online.school.school.security.JwtUtils;
import com.online.school.school.security.UserDetailsServiceImpl;

@Service
public class AdminJpaDaoService {

    @Autowired
    private AdminRepo adminRepoService;
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


    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public String loginAdmin(Admin admin) {
        Optional<Admin> optionalAdmin = adminRepoService.findByAdminName(admin.getAdminName());

        if (optionalAdmin.isPresent()) {
            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            admin.getAdminName(),
                            admin.getAdminPassword()));

            // Load UserDetails for JWT
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(admin.getAdminName());
            return jwtUtils.generateToken(userDetails);
        } else {
            throw new UsernameNotFoundException("Incorrect username or password");
        }
    }

        public Admin createNewAdmin(Admin reqAdmin) {
        // Validate input
        // if (reqAdmin.getAdminName() == null || reqAdmin.getAdminName().trim().isEmpty()) {
        //     throw new IllegalArgumentException("Admin name cannot be null or empty");
        // }
        // if (reqAdmin.getAdminPassword() == null || reqAdmin.getAdminPassword().trim().isEmpty()) {
        //     throw new IllegalArgumentException("Admin password cannot be null or empty");
        // }

        // Optional<Admin> existingAdminUserName = adminRepoService.findByAdminName(reqAdmin.getAdminName());
        // Optional<?> existingTeacherName = teacherRepo.findByTeacherName(reqAdmin.getAdminName());
        // Optional<?> existingUserEntName = studentRepo.findByStudentName(reqAdmin.getAdminName());

        // if (existingAdminUserName.isPresent() || existingTeacherName.isPresent() || existingUserEntName.isPresent()) {
        //     throw new IllegalArgumentException("Admin username is already used");
        // }

        // Create new admin
        Admin newAdmin = Admin.builder()
                .adminName(reqAdmin.getAdminName())
                .adminPassword(passwordEncoder.encode(reqAdmin.getAdminPassword()))
                .roles(Set.of(Role.ADMIN))
                .build();

        return adminRepoService.save(newAdmin);
    }

    public Admin findAdminById(int adminId) {
        Optional<Admin> adminOptional = adminRepoService.findById(adminId);
        if (adminOptional.isPresent()) {
            return adminOptional.get();
        } else {
            throw new AdminNotFoundException("Admin By Id: " + adminId + " Is Not Found");
        }
    }

}
