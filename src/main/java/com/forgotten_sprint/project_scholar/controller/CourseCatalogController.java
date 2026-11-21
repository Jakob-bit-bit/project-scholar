package com.forgotten_sprint.project_scholar.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.forgotten_sprint.project_scholar.model.Course;
import com.forgotten_sprint.project_scholar.model.CourseStatus;
import com.forgotten_sprint.project_scholar.repository.CourseCatalogRepository;

@Controller
public class CourseCatalogController {

    private static final List<String> SORTABLE_FIELDS = List.of("title", "startDate");

    private final CourseCatalogRepository courseCatalogRepository;

    public CourseCatalogController(CourseCatalogRepository courseCatalogRepository) {
        this.courseCatalogRepository = courseCatalogRepository;
    }
    
    @GetMapping("/catalog")
    public String showCatalog(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "title") String sort,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) CourseStatus status,
            Model model
    ) {
        if (!SORTABLE_FIELDS.contains(sort)) {
            sort = "title";
        }
        Sort.Direction dir = direction.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1), Sort.by(dir, sort));

        // The form sends "" for "All", so turn a blank category into null
        String categoryFilter = (category == null || category.isBlank()) ? null : category;

        Page<Course> coursePage = courseCatalogRepository.search(categoryFilter, status, pageable);

        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("totalElements", coursePage.getTotalElements());
        model.addAttribute("currentPage", coursePage.getNumber());
        model.addAttribute("pageSize", coursePage.getSize());
        model.addAttribute("hasPrevious", coursePage.hasPrevious());
        model.addAttribute("hasNext", coursePage.hasNext());

        model.addAttribute("categories", courseCatalogRepository.findAllCategories());
        model.addAttribute("statuses", CourseStatus.values());
        model.addAttribute("category", categoryFilter);
        model.addAttribute("status", status);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("pageTitle", "Course Catalog");

        return "catalog";
    }
}