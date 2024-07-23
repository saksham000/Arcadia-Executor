package com.online.school.school.controllers;

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
import com.online.school.school.exceptions.AdminNotFoundException;
import com.online.school.school.service.AdminDaoService;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private AdminDaoService adminDaoService;

    @GetMapping(path = "admin")
    public List<Admin> fetchAllAdmins() {
        return adminDaoService.listAllAdmins();
    }

    @PostMapping(path = "admin")
    public Admin addNewAdmin(@RequestBody Admin admin) {
        return adminDaoService.createNewAdmin(admin);
    }

    @PostMapping(path = "admin/login")
    public Admin loginAdmin(@RequestBody Admin admin) {
        try {
            return adminDaoService.loginAdmin(admin);
        } catch (AdminNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(path = "admin/{adminId}")
    public Admin findAdminById(@PathVariable int adminId) {
        try {
            return adminDaoService.findAdminById(adminId);
        } catch (AdminNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping(path = "admin/{adminId}")
    public void deleteAdminById(@PathVariable int adminId) {
        try {
            adminDaoService.deleteAdminById(adminId);
        } catch (AdminNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
