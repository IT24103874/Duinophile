package com.duinophile.controller;

import com.duinophile.model.Lesson;
import com.duinophile.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping("/create/{courseId}")
    public String showCreateForm(@PathVariable String courseId, Model model) {
        Lesson lesson = new Lesson();
        lesson.setCourseId(courseId);
        model.addAttribute("lesson", lesson);
        model.addAttribute("view", "create-lesson");
        return "layout";
    }

    @PostMapping("/create")
    public String createLesson(@Valid @ModelAttribute("lesson") Lesson lesson, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("view", "create-lesson");
            return "layout";
        }
        lessonService.createLesson(lesson);
        return "redirect:/courses/view/" + lesson.getCourseId();
    }

    @GetMapping("/view/{id}")
    public String viewLesson(@PathVariable String id, Model model) {
        lessonService.getById(id).ifPresent(lesson -> model.addAttribute("lesson", lesson));
        model.addAttribute("view", "lesson-view");
        return "layout";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        lessonService.getById(id).ifPresent(lesson -> model.addAttribute("lesson", lesson));
        model.addAttribute("view", "edit-lesson");
        return "layout";
    }

    @PostMapping("/update/{id}")
    public String updateLesson(@PathVariable String id, @Valid @ModelAttribute("lesson") Lesson lesson,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("view", "edit-lesson");
            return "layout";
        }
        lessonService.updateLesson(id, lesson);
        return "redirect:/lessons/view/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteLesson(@PathVariable String id) {
        String courseId = lessonService.getById(id).map(Lesson::getCourseId).orElse("");
        lessonService.deleteLesson(id);
        return "redirect:/courses/view/" + courseId;
    }

    @PostMapping("/{id}/add-quiz-question")
    public String addQuizQuestion(@PathVariable String id, @ModelAttribute Lesson.QuizQuestion question) {
        lessonService.getById(id).ifPresent(lesson -> {
            lesson.getQuiz().add(question);
            lessonService.updateLesson(id, lesson);
        });
        return "redirect:/lessons/view/" + id;
    }
}
