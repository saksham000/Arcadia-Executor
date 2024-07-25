package com.online.school.school.service.jpaDaoService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.school.school.exceptions.ClassNotFoundException;
import com.online.school.school.databasefiles.StClass;
import com.online.school.school.databasefiles.jpaRepositories.StClassRepo;

@Service
public class StClassJpaDaoService {

    @Autowired
    private StClassRepo stClassRepoService;

    public List<StClass> listAllClasses() {
        return stClassRepoService.findAll();
    }

    public StClass findClassById(int clasId) {
        Optional<StClass> classOptional = stClassRepoService.findById(clasId);
        if (classOptional.isPresent()) {
            return classOptional.get();
        } else {
            throw new ClassNotFoundException("Class with Id: " + clasId + " Not Found !");
        }
    }

    public StClass createNewClass(StClass newClass) {
        newClass.setTeacher(null);
        stClassRepoService.save(newClass);
        return newClass;
    }

    public void deleteClassById(int cId) {
        StClass storeClass = findClassById(cId);
        stClassRepoService.deleteById(storeClass.getClassId());
    }

}
