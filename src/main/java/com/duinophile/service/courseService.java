package com.duinophile.service;

import com.duinophile.model.course;
import com.duinophile.repository.courseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class courseService {
    @Autowired
    private courseRepo courseRepo;

    public void printAllCourses() {
        List<course> courseList = courseRepo.findAll();
        System.out.println("--- All Courses in Database ---");
        courseList.forEach(course -> {
            course.printInfo();
        });
    }
}
