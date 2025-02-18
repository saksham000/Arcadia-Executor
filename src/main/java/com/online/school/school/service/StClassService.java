package com.online.school.school.service;

import java.util.List;

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
        StClass classOptional = stClassRepoService.findById(clasId)
                .orElseThrow(() -> new NotFoundException("Class with Id: " + clasId + " Not Found !"));
        return classOptional;
    }

    public StClass createNewClass(StClass reqClass) {
        StClass newClass = StClass.builder()
                .className(reqClass.getClassName())
                .students(null)
                .teacher(null)
                .build();
        return stClassRepoService.save(newClass);
    }

    public StClass updateClass(StClass reqClass) {
        StClass newClass = findClassById(reqClass.getClassId());

        if (reqClass.getClassName() != null) {
            newClass.setClassName(reqClass.getClassName());
        }
        if (reqClass.getStudents() != null) {
            newClass.setStudents(reqClass.getStudents());
        }
        if (reqClass.getTeacher() != null) {
            newClass.setTeacher(reqClass.getTeacher());
        }
        return stClassRepoService.save(newClass);
    }

    public void deleteClassById(Long cId) {
        StClass storeClass = findClassById(cId);
        stClassRepoService.delete(storeClass);
    }
}
