package com.online.school.school.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.school.school.customApiResponse.ApiResponse;
import com.online.school.school.entity.Admin;
import com.online.school.school.service.AdminService;

import java.util.List;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping(path = "/all-admin")
    public ResponseEntity<ApiResponse<List<Admin>>> fetchAllAdmins() {
        try {
            List<Admin> admins = adminService.allAdmins();
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Admins fetched successfully", admins),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Error fetching admins: " + e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

    @PostMapping(path = "/create-admin")
    public ResponseEntity<ApiResponse<Admin>> createAdmin(@RequestBody Admin admin) {
        try {
            Admin newAdmin = adminService.createNewAdmin(admin);
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.OK.value(), "Admin created successfully", newAdmin),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Error creating admin: " + e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

    @PutMapping(path = "/update-admin")
    ResponseEntity<ApiResponse<Admin>> updateAdmin(@RequestBody Admin admin) {
        try {
            Admin updatedAdmin = adminService.updateAdmin(admin);
            return new ResponseEntity<>(new ApiResponse<Admin>(HttpStatus.OK.value(), "Success", updatedAdmin),
                    HttpStatus.OK);
        } catch (Exception IllegalArgumentException) {
            return new ResponseEntity<>(
                    new ApiResponse<Admin>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            IllegalArgumentException.getMessage(), null),
                    HttpStatus.OK);
        }
    }

    @GetMapping(path = "/find-admin/{adminId}")
    public ResponseEntity<ApiResponse<Admin>> findAdminById(@PathVariable Long adminId) {
        try {
            Admin admin = adminService.findAdminById(adminId);
            return new ResponseEntity<>(new ApiResponse<Admin>(HttpStatus.OK.value(), "Success", admin),
                    HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse<Admin>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null),
                    HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "/delete-admin")
    public ResponseEntity<ApiResponse<Void>> deleteAdminById() {
        try {
            adminService.deleteAdmin();
            return new ResponseEntity<>(
                    new ApiResponse<Void>(HttpStatus.OK.value(), "Admin deleted successfully", null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<Void>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null),
                    HttpStatus.OK);
        }
    }
}
