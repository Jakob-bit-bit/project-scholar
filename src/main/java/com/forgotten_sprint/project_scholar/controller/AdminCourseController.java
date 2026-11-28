package com.forgotten_sprint.project_scholar.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.forgotten_sprint.project_scholar.dto.CourseForm;
import com.forgotten_sprint.project_scholar.model.Course;
import com.forgotten_sprint.project_scholar.model.CourseStatus;
import com.forgotten_sprint.project_scholar.model.Instructor;
import com.forgotten_sprint.project_scholar.repository.CourseCatalogRepository;
import com.forgotten_sprint.project_scholar.service.CourseService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminCourseController {

    private final CourseCatalogRepository courseRepository;
    private final CourseService courseService;

    public AdminCourseController(CourseCatalogRepository courseRepository,
                                 CourseService courseService) {
        this.courseRepository = courseRepository;
        this.courseService = courseService;
    }

    // ---------- DASHBOARD & TABLE ----------

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

    // ---------- EDIT ----------

    @GetMapping("/courses/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model,
                               RedirectAttributes redirectAttributes) {
        Optional<Course> found = courseRepository.findById(id);
        if (found.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "That course no longer exists.");
            return "redirect:/admin/courses";
        }

        model.addAttribute("courseForm", toForm(found.get()));
        addFormData(model, id);
        return "admin/edit-course";
    }

    @PostMapping("/courses/{id}/edit")
    public String updateCourse(@PathVariable Long id,
                               @Valid @ModelAttribute("courseForm") CourseForm courseForm,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        Optional<Course> found = courseRepository.findById(id);
        if (found.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "That course no longer exists.");
            return "redirect:/admin/courses";
        }

        Instructor instructor = null;
        if (courseForm.getInstructorId() != null) {
            instructor = courseService.getInstructorById(courseForm.getInstructorId());
            if (instructor == null) {
                bindingResult.rejectValue("instructorId", "error.instructor",
                        "Selected instructor does not exist");
            }
        }

        String code = courseForm.getCourseCode() == null ? "" : courseForm.getCourseCode().trim();
        if (!code.isEmpty() && courseRepository.existsByCourseCodeAndCourseIdNot(code, id)) {
            bindingResult.rejectValue("courseCode", "error.courseCode",
                    "This course code is already registered");
        }

        if (courseForm.getStartDate() != null && courseForm.getEndDate() != null
                && !courseForm.getEndDate().isAfter(courseForm.getStartDate())) {
            bindingResult.rejectValue("endDate", "error.endDate",
                    "End date must be after start date");
        }

        if (bindingResult.hasErrors()) {
            addFormData(model, id);
            return "admin/edit-course";
        }

        Course course = found.get();
        course.setInstructor(instructor);
        course.setCourseCode(code);
        course.setTitle(courseForm.getTitle().trim());
        course.setDescription(courseForm.getDescription().trim());
        course.setCategory(courseForm.getCategory());
        course.setStartDate(courseForm.getStartDate());
        course.setEndDate(courseForm.getEndDate());
        course.setMaxStudents(courseForm.getMaxStudents());
        course.setStatus(courseForm.getStatus());
        courseRepository.save(course);

        redirectAttributes.addFlashAttribute("successMessage",
                "Course \"" + course.getTitle() + "\" was updated.");
        return "redirect:/admin/courses";
    }

    // ---------- DELETE ----------

    @PostMapping("/courses/{id}/delete")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Course> found = courseRepository.findById(id);
        if (found.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "That course no longer exists. It may have already been deleted.");
            return "redirect:/admin/courses";
        }

        try {
            courseRepository.delete(found.get());
            redirectAttributes.addFlashAttribute("successMessage",
                    "Course \"" + found.get().getTitle() + "\" was deleted.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "This course can't be deleted because other records depend on it.");
        }
        return "redirect:/admin/courses";
    }

    // ---------- helpers ----------

    private void addFormData(Model model, Long courseId) {
        model.addAttribute("instructors", courseService.getAllInstructors());
        model.addAttribute("statuses", CourseStatus.values());
        model.addAttribute("courseId", courseId);
        model.addAttribute("pageTitle", "Edit Course");
    }

    private CourseForm toForm(Course c) {
        CourseForm form = new CourseForm();
        form.setInstructorId(c.getInstructor().getInstructorId());
        form.setCourseCode(c.getCourseCode());
        form.setTitle(c.getTitle());
        form.setDescription(c.getDescription());
        form.setCategory(c.getCategory());
        form.setStartDate(c.getStartDate());
        form.setEndDate(c.getEndDate());
        form.setMaxStudents(c.getMaxStudents());
        form.setStatus(c.getStatus());
        return form;
    }
}