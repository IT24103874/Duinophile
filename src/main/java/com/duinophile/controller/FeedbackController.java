package com.duinophile.controller;

import com.duinophile.model.Feedback;
import com.duinophile.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/course/{courseId}")
    public String viewFeedback(@PathVariable String courseId, Model model) {
        model.addAttribute("feedbacks", feedbackService.getFeedbackByCourseId(courseId));
        Feedback newFeedback = new Feedback();
        newFeedback.setCourseId(courseId);
        model.addAttribute("feedback", newFeedback);
        model.addAttribute("view", "feedback-list");
        return "layout";
    }

    @PostMapping("/create")
    public String createFeedback(@ModelAttribute Feedback feedback) {
        feedbackService.createFeedback(feedback);
        return "redirect:/feedback/course/" + feedback.getCourseId();
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        feedbackService.getFeedbackById(id).ifPresent(fb -> model.addAttribute("feedback", fb));
        model.addAttribute("view", "edit-feedback");
        return "layout";
    }

    @PostMapping("/update/{id}")
    public String updateFeedback(@PathVariable String id, @ModelAttribute Feedback feedback) {
        feedbackService.updateFeedback(id, feedback);
        return "redirect:/feedback/course/" + feedback.getCourseId();
    }

    @GetMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable String id) {
        String courseId = feedbackService.getFeedbackById(id).map(Feedback::getCourseId).orElse("");
        feedbackService.deleteFeedback(id);
        return "redirect:/feedback/course/" + courseId;
    }
}
