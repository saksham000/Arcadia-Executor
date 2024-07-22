package com.online.school.school.service;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.online.school.school.databasefiles.Admin;
import com.online.school.school.exceptions.AdminNotFoundException;

import java.util.List;
import java.util.ArrayList;

@Service
public class AdminDaoService {

    private static List<Admin> admins = new ArrayList<>();
    private static int adminId = 1;

    static{
        admins.add(new Admin(adminId++, "saksham", "saksham"));
        admins.add(new Admin(adminId++, "test", "test"));
    }

    public List<Admin> listAllAdmins(){
        return admins;
    }

    public Admin createNewAdmin(Admin admin){
        admin.setAdminId(adminId++);
        admins.add(admin);
        return admin;
    }

    public Admin loginAdmin(Admin admin){
        Optional<Admin> adminOptional = admins.stream().filter(a -> a.getAdminId() == admin.getAdminId() && a.getAdminName().equalsIgnoreCase(admin.getAdminName()) && a.getAdminPassword().equalsIgnoreCase(admin.getAdminPassword())).findFirst();
        if(adminOptional.isPresent()){
            return adminOptional.get();
        }else{
            throw new AdminNotFoundException("Please Check Your Credentials !");
        }
    }

    public Admin findAdminById(int adminId){
        Optional<Admin> adminOptional = admins.stream().filter(admin -> admin.getAdminId() == adminId).findFirst();
        if(adminOptional.isPresent()){
            return adminOptional.get();
        }else{
            throw new AdminNotFoundException("Admin By Id: " + adminId + " Is Not Found");
        }
    }

    
}
