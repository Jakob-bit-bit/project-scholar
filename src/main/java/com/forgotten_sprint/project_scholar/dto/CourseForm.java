package com.forgotten_sprint.project_scholar.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.forgotten_sprint.project_scholar.model.CourseStatus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CourseForm {
	@NotNull(message = "Please select an instructor")
	private Long instructorId;
	
	@NotBlank(message = "Course code is required")
	@Size(max = 20, message = "Course code cannot exceed 20 characters")
	private String courseCode;
	
	@NotBlank(message = "Course title is required")
	@Size(max = 100, message = "Course title cannot exceed 100 characters")
	private String title;
	
	@NotBlank(message = "Course description is required")
	@Size(max = 1000, message = "Description cannot exceed 1000 characters")
	private String description;
	@NotBlank(message = "Please select a category")
    @Pattern(
        regexp = "Programming|Mathematics|Science|Business|Languages",
        message = "Please select a valid category"
    )
    private String category;
	@NotNull(message = "Start date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @NotNull(message = "Maximum students is required")
    @Min(value = 1, message = "Maximum students must be at least 1")
    private Integer maxStudents;

    @NotNull(message = "Please select a status")
    private CourseStatus status = CourseStatus.INACTIVE;

    public CourseForm() {
    }

	public Long getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(Long instructorId) {
		this.instructorId = instructorId;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getMaxStudents() {
		return maxStudents;
	}

	public void setMaxStudents(Integer maxStudents) {
		this.maxStudents = maxStudents;
	}

	public CourseStatus getStatus() {
		return status;
	}

	public void setStatus(CourseStatus status) {
		this.status = status;
	}
	
}
