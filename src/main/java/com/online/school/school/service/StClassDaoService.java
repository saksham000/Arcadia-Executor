package com.online.school.school.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.online.school.school.exceptions.ClassNotFoundException;
import com.online.school.school.databasefiles.StClass;

@Service
public class StClassDaoService {

    private static List<StClass> classes = new ArrayList<>();
    private static int classId = 1;
    static {
        classes.add(new StClass(classId++, "Xth Red", null));
        classes.add(new StClass(classId++, "XIIth Red", null));
    }

    public List<StClass> listAllClasses() {
        return classes;
    }

    public StClass findClassById(int clasId) {
        Optional<StClass> classOptional = classes.stream().filter(c -> c.getClassId() == clasId).findFirst();
        if (classOptional.isPresent()) {
            return classOptional.get();
        } else {
            throw new ClassNotFoundException("Class with Id: " + clasId + " Not Found !");
        }
    }

    public StClass createNewClass(StClass newClass){
        newClass.setClassId(classId++);
        newClass.setTeacher(null);
        // newClass.setStudents(null);
        classes.add(newClass);
        return newClass;
    }

    public void deleteClassById(int cId) {
        findClassById(cId);
        classes.removeIf(c -> c.getClassId() == cId);
    }

}
