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
import com.online.school.school.entity.jpaRepositories.AdminRepo;
import com.online.school.school.entity.jpaRepositories.StudentRepo;
import com.online.school.school.entity.jpaRepositories.TeacherRepo;
import com.online.school.school.exceptions.CreationException;
import com.online.school.school.security.JwtUtils;
import com.online.school.school.security.UserDetailsServiceImpl;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepoService;

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

    public String loginAdmin(Admin admin) {

        Optional<Admin> optionalAdmin = adminRepoService.findByAdminName(admin.getAdminName());

        if (optionalAdmin.isPresent()) {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            admin.getAdminName(),
                            admin.getAdminPassword()));

            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(admin.getAdminName());
            return jwtUtils.generateToken(userDetails);
        } else {
            throw new UsernameNotFoundException("Incorrect username or password");
        }
    }

    public Admin getAuthenticatedAdmin() {

        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();

        return adminRepoService.findByAdminName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found!"));
    }

    public List<Admin> allAdmins() {
        return adminRepoService.findAll();
    }

    public Admin createNewAdmin(Admin reqAdmin) {
        // Validate input
        if (reqAdmin.getAdminName() == null || reqAdmin.getAdminName().trim().isEmpty()) {
            throw new CreationException("Admin name cannot be null or empty");
        }
        if (reqAdmin.getAdminPassword() == null || reqAdmin.getAdminPassword().trim().isEmpty()) {
            throw new CreationException("Admin password cannot be null or empty");
        }

        Optional<Admin> existingAdminUserName = adminRepoService.findByAdminName(reqAdmin.getAdminName());
        Optional<?> existingTeacherName = teacherRepo.findByTeacherName(reqAdmin.getAdminName());
        Optional<?> existingUserEntName = studentRepo.findByStudentName(reqAdmin.getAdminName());

        if (existingAdminUserName.isPresent() || existingTeacherName.isPresent() || existingUserEntName.isPresent()) {
            throw new CreationException("Admin username is already used");
        }

        Admin newAdmin = Admin.builder()
                .adminName(reqAdmin.getAdminName())
                .adminPassword(passwordEncoder.encode(reqAdmin.getAdminPassword()))
                .roles(Set.of(Role.ADMIN))
                .build();

        return adminRepoService.save(newAdmin);
    }

    public Admin updateAdmin(Admin reqAdmin) {
        Admin authenticatedAdmin = getAuthenticatedAdmin();

        if (authenticatedAdmin.getAdminId() == null) {
            throw new IllegalArgumentException("Authenticated Admin ID is null");
        }

        if (reqAdmin.getAdminName() != null) {
            authenticatedAdmin.setAdminName(reqAdmin.getAdminName());
        }
        if (reqAdmin.getAdminPassword() != null && !reqAdmin.getAdminPassword().isEmpty()) {
            authenticatedAdmin.setAdminPassword(passwordEncoder.encode(reqAdmin.getAdminPassword()));
        }

        return adminRepoService.save(authenticatedAdmin);
    }

    public Admin findAdminById(Long adminId) {
        Admin adminOptional = adminRepoService.findById(adminId)
                .orElseThrow(() -> new UsernameNotFoundException("admin not found"));
        return adminOptional;
    }

    public void deleteAdmin() {
        Admin admin = findAdminById(getAuthenticatedAdmin().getAdminId());
        adminRepoService.delete(admin);
    }

}
