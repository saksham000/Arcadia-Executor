package com.online.school.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.school.school.entity.StClass;
import com.online.school.school.entity.jpaRepositories.StClassRepo;
import com.online.school.school.exceptions.NotFoundException;

@Service
public class StClassService {

    @Autowired
    private StClassRepo stClassRepoService;

    public List<StClass> listAllClasses() {
        return stClassRepoService.findAll();
    }

    public StClass findClassById(Long clasId) {
        Optional<StClass> classOptional = stClassRepoService.findById(clasId);
        if (classOptional.isPresent()) {
            return classOptional.get();
        } else {
            throw new NotFoundException("Class with Id: " + clasId + " Not Found !");
        }
    }

    public StClass createNewClass(StClass newClass) {
        newClass.setTeacher(null);
        stClassRepoService.save(newClass);
        return newClass;
    }

    public void deleteClassById(Long cId) {
        StClass storeClass = findClassById(cId);
        stClassRepoService.deleteById(storeClass.getClassId());
    }

}
