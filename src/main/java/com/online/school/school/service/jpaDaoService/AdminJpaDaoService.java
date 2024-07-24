package com.online.school.school.service.jpaDaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.school.school.databasefiles.Admin;
import com.online.school.school.databasefiles.jpaRepositories.AdminRepo;

@Service
public class AdminJpaDaoService {

    @Autowired
    private AdminRepo adminRepoService;

    public Boolean loginAdmin(Admin admin) {
        Admin admins = adminRepoService.findById(admin.getAdminId()).orElse(null);
        return admins != null && admins.getAdminName().equals(admin.getAdminName())
                && admins.getAdminPassword().equals(admin.getAdminPassword());
    }

}
