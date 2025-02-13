package com.online.school.school.controllers.jpaController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.online.school.school.databasefiles.Admin;
import com.online.school.school.databasefiles.jpaRepositories.AdminRepo;
import com.online.school.school.exceptions.AdminNotFoundException;
import com.online.school.school.service.jpaDaoService.AdminJpaDaoService;

import java.util.List;

@RestController
@RequestMapping(path = "/admin")
public class AdminJpaController {

    @Autowired
    private AdminJpaDaoService adminJpaDaoService;

    @Autowired
    private AdminRepo adminRepoService;

    @GetMapping(path = "/")
    public String rootPage(){
        return "Welcome To Root Page !";
    }

    @GetMapping(path = "/all-admin")
    public List<Admin> fetchAllAdmins() {
        return adminRepoService.findAll();
    }

    @PostMapping(path = "/create-admin")
    ResponseEntity<Admin> createAdmin(@RequestBody Admin admin){
        try{
            Admin newAdmin = adminJpaDaoService.createNewAdmin(admin);
            return ResponseEntity.ok(newAdmin);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

    @PostMapping(path = "/admin-login")
    ResponseEntity<String> loginAdmin(@RequestBody Admin admin){
        try {
            String token = adminJpaDaoService.loginAdmin(admin);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Login failed: " + e.getMessage());
        }
    }

    @GetMapping(path = "find-admin/{adminId}")
    public Admin findAdminById(@PathVariable int adminId) {
        try {
            return adminJpaDaoService.findAdminById(adminId);
        } catch (AdminNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping(path = "delete-admin/{adminId}")
    public void deleteAdminById(@PathVariable int adminId) {
        try {
            adminJpaDaoService.findAdminById(adminId);
            adminRepoService.deleteById(adminId);
        } catch (AdminNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
