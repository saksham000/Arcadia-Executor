package com.online.school.school.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.school.school.entity.Admin;
import com.online.school.school.service.AdminService;

@RestController
@RequestMapping(path = "/public")
public class PublicController {

    @Autowired
    private AdminService adminService;

    @PostMapping(path = "/login-admin")
    ResponseEntity<String> loginAdmin(@RequestBody Admin admin){
        try {
            String token = adminService.loginAdmin(admin);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Login failed: " + e.getMessage());
        }
    }


    
}
