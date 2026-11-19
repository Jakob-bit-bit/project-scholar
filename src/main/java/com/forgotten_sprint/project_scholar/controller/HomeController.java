package com.forgotten_sprint.project_scholar.controller;

import com.forgotten_sprint.project_scholar.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CourseService courseService;

    public HomeController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/explore-learning")
    public String exploreLearning(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "explore-learning";
    }
}