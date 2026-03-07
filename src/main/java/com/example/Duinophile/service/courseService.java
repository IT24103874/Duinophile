package com.example.Duinophile.service;

import com.example.Duinophile.model.course;
import com.example.Duinophile.repository.courseRepo;
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
