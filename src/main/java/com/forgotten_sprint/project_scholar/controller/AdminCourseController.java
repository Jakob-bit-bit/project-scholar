package com.forgotten_sprint.project_scholar.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forgotten_sprint.project_scholar.model.CourseStatus;
import com.forgotten_sprint.project_scholar.repository.CourseCatalogRepository;

@Controller
@RequestMapping("/admin")
public class AdminCourseController {

    private final CourseCatalogRepository courseRepository;

    public AdminCourseController(CourseCatalogRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public String dashboard(Model model) {
        Map<CourseStatus, Long> statusCounts = new LinkedHashMap<>();
        for (CourseStatus s : CourseStatus.values()) {
            statusCounts.put(s, courseRepository.countByStatus(s));
        }

        model.addAttribute("pageTitle", "Admin Dashboard");
        model.addAttribute("totalCourses", courseRepository.count());
        model.addAttribute("statusCounts", statusCounts);
        return "admin/dashboard";
    }

    @GetMapping("/courses")
    public String manageCourses(Model model) {
        model.addAttribute("pageTitle", "Manage Courses");
        model.addAttribute("courses", courseRepository.findAll(Sort.by("title")));
        return "admin/courses";
    }
}