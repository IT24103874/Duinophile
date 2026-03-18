package com.duinophile.controller;

import com.duinophile.model.course;
import com.duinophile.repository.courseRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class courseController {
    private final courseRepo courseRepo;

    public courseController(courseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    @GetMapping("/courses")
    public String courses(Model model) {
        List<course> courses = courseRepo.findAll();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @GetMapping("/createCourse")
    public String createCourse(){
        return "createCourse";
    }

    @PostMapping("/AddCourseToDataBase")
    public String addCourse(@RequestParam String name, @RequestParam String description, @RequestParam String material, @RequestParam String level, @RequestParam String submitType, @RequestParam int id){
        if (submitType.equals("delete")) {
            courseRepo.deleteById(id);
        } else if (submitType.equals("create")) {
            create(name, description, material, level);
        } else if (submitType.equals("edit")) {
            edit(id, name, description, material, level);
        }
        return "redirect:/courses";
    }

    public void create(String name, String description, String material, String level){
        int id = courseRepo.findAll().size() + 1;
        course courseNew = new course(id, name, description, material, level);
        courseRepo.save(courseNew);
    }

    public void edit(int id, String name, String description, String material, String level){
        course courseNew = new course(id, name, description, material, level);
        courseRepo.save(courseNew);
    }

    @GetMapping("/course/{id}")
    public String viewCourse(@PathVariable int id, Model model){
        course course = courseRepo.findById(id).get();
        model.addAttribute("course", course);
        return "course";
    }

    @GetMapping("/editCourse/{id}")
    public String editCourse(@PathVariable int id, Model model){
        course course = courseRepo.findById(id).get();
        model.addAttribute("course", course);
        return "createCourse";
    }


}
