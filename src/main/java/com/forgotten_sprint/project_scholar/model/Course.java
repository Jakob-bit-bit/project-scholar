package com.forgotten_sprint.project_scholar.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity 
@Table(name = "courses")
public class Course {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long courseId;

	    @ManyToOne
	    @JoinColumn(name = "instructor_id", nullable = false)
	    @NotNull(message = "Please select an instructor")
	    private Instructor instructor;

	    @NotBlank(message = "Course code is required")
	    @Size(max = 20)
	    @Column(nullable = false, unique = true, length = 20)
	    private String courseCode;

	    @NotBlank(message = "Course title is required")
	    @Size(max = 100)
	    @Column(nullable = false, length = 100)
	    private String title;

	    @NotBlank(message = "Course description is required")
	    @Size(max = 1000)
	    @Column(nullable = false, length = 1000)
	    private String description;

	    @NotBlank(message = "Please select a category")
	    @Column(nullable = false)
	    private String category;

	    @NotNull(message = "Start date is required")
	    @Column(nullable = false)
	    private LocalDate startDate;

	    @NotNull(message = "End date is required")
	    @Column(nullable = false)
	    private LocalDate endDate;

	    @NotNull(message = "Maximum students is required")
	    @Min(value = 1, message = "Maximum students must be at least 1")
	    private Integer maxStudents;

	    @NotNull(message = "Please select a course status")
	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private CourseStatus status = CourseStatus.INACTIVE;

	    @Column(nullable = false, updatable = false)
	    private LocalDateTime createdAt;

	    public Course() {
	    }

	    @PrePersist
	    public void onCreate() {
	        if (createdAt == null) {
	            createdAt = LocalDateTime.now();
	        }
	    }

	    @AssertTrue(message = "End date must be after start date")
	    public boolean isEndDateValid() {
	        return startDate == null || endDate == null
	                || endDate.isAfter(startDate);
	    }

		public Long getCourseId() {
			return courseId;
		}

		public void setCourseId(Long courseId) {
			this.courseId = courseId;
		}

		public Instructor getInstructor() {
			return instructor;
		}

		public void setInstructor(Instructor instructor) {
			this.instructor = instructor;
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

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		
}
