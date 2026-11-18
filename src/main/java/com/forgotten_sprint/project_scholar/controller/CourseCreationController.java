package com.forgotten_sprint.project_scholar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.forgotten_sprint.project_scholar.dto.CourseForm;
import com.forgotten_sprint.project_scholar.model.Course;
import com.forgotten_sprint.project_scholar.model.CourseStatus;
import com.forgotten_sprint.project_scholar.model.Instructor;
import com.forgotten_sprint.project_scholar.service.CourseService;

import jakarta.validation.Valid;

@Controller
public class CourseCreationController {
	private final CourseService courseService;
	public CourseCreationController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses/new")
    public String showCreateCourseForm(Model model) {

        model.addAttribute("courseForm", new CourseForm());
        model.addAttribute("instructors",
                courseService.getAllInstructors());
        model.addAttribute("statuses", CourseStatus.values());
        model.addAttribute("pageTitle", "Create New Course");

        return "create-course";
    }

    @PostMapping("/courses")
    public String createCourse(
            @Valid @ModelAttribute("courseForm") CourseForm courseForm,
            BindingResult bindingResult,
            Model model) {

        Instructor instructor = null;

        if (courseForm.getInstructorId() != null) {
            instructor = courseService.getInstructorById(
                    courseForm.getInstructorId());

            if (instructor == null) {
                bindingResult.rejectValue(
                        "instructorId",
                        "error.instructor",
                        "Selected instructor does not exist"
                );
            }
        }

        if (courseForm.getCourseCode() != null
                && !courseForm.getCourseCode().isBlank()
                && courseService.courseCodeExists(
                        courseForm.getCourseCode().trim())) {

            bindingResult.rejectValue(
                    "courseCode",
                    "error.courseCode",
                    "This course code is already registered"
            );
        }

        if (courseForm.getStartDate() != null
                && courseForm.getEndDate() != null
                && !courseForm.getEndDate().isAfter(
                        courseForm.getStartDate())) {

            bindingResult.rejectValue(
                    "endDate",
                    "error.endDate",
                    "End date must be after start date"
            );
        }

        if (bindingResult.hasErrors()) {

            model.addAttribute("instructors",
                    courseService.getAllInstructors());
            model.addAttribute("statuses", CourseStatus.values());
        model.addAttribute("pageTitle", "Create New Course");

            return "create-course";
        }

        Course course = new Course();

        course.setInstructor(instructor);
        course.setCourseCode(courseForm.getCourseCode().trim());
        course.setTitle(courseForm.getTitle().trim());
        course.setDescription(courseForm.getDescription().trim());
        course.setCategory(courseForm.getCategory());
        course.setStartDate(courseForm.getStartDate());
        course.setEndDate(courseForm.getEndDate());
        course.setMaxStudents(courseForm.getMaxStudents());
        course.setStatus(courseForm.getStatus());

        courseService.saveCourse(course);

        return "redirect:/courses";
    }
}
