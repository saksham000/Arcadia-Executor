package com.online.school.school.controllers.jpaController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.school.school.databasefiles.Admin;
import com.online.school.school.service.jpaDaoService.AdminJpaDaoService;

@RestController
@RequestMapping(path = "/public")
public class PublicController {

    @Autowired
    private AdminJpaDaoService adminJpaDaoService;

    @PostMapping(path = "/login-admin")
    ResponseEntity<String> loginAdmin(@RequestBody Admin admin){
        try {
            String token = adminJpaDaoService.loginAdmin(admin);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Login failed: " + e.getMessage());
        }
    }


    
}
