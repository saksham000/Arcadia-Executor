package com.online.school.school.controllers.jpaController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.online.school.school.databasefiles.Admin;
import com.online.school.school.databasefiles.jpaRepositories.AdminRepo;
import com.online.school.school.exceptions.AdminNotFoundException;
import com.online.school.school.service.jpaDaoService.AdminJpaDaoService;

import java.util.List;

@RestController
public class AdminJpaController {

    @Autowired
    private AdminJpaDaoService adminJpaDaoService;

    @Autowired
    private AdminRepo adminRepoService;

    @GetMapping(path = "/")
    public String rootPage(){
        return "Welcome To Root Page !";
    }

    @GetMapping(path = "admin")
    public List<Admin> fetchAllAdmins() {
        return adminRepoService.findAll();
    }

    @PostMapping(path = "admin")
    public Admin addNewAdmin(@RequestBody Admin admin) {
        return adminRepoService.save(admin);

    }

    @PostMapping(path = "admin/login")
    public boolean loginAdmin(@RequestBody Admin admin) {

        if (adminJpaDaoService.loginAdmin(admin) == true) {
            return adminJpaDaoService.loginAdmin(admin);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials");
        }
    }

    @GetMapping(path = "admin/{adminId}")
    public Admin findAdminById(@PathVariable int adminId) {
        try {
            return adminJpaDaoService.findAdminById(adminId);
        } catch (AdminNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping(path = "admin/{adminId}")
    public void deleteAdminById(@PathVariable int adminId) {
        try {
            adminJpaDaoService.findAdminById(adminId);
            adminRepoService.deleteById(adminId);
        } catch (AdminNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
