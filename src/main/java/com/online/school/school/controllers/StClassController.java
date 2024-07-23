package com.online.school.school.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.school.school.databasefiles.StClass;
import com.online.school.school.service.StClassDaoService;

@RestController
public class StClassController {
    @Autowired
    private StClassDaoService stClassDaoService;

    @GetMapping(path = "classes")
    public List<StClass> fetchAllClasses(){
        return stClassDaoService.listAllClasses();
    }
}
