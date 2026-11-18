package com.forgotten_sprint.project_scholar.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.forgotten_sprint.project_scholar.model.Course;
import com.forgotten_sprint.project_scholar.model.Instructor;
import com.forgotten_sprint.project_scholar.repository.CourseRepository;
import com.forgotten_sprint.project_scholar.repository.InstructorRepository;

@Service
public class CourseService {
	private final CourseRepository courseRepository;
	private final InstructorRepository instructorRepository;
	
	public CourseService(CourseRepository courseRepository,
			InstructorRepository instructorRepository) {
		this.courseRepository = courseRepository;
		this.instructorRepository = instructorRepository;
	}
	
	public List<Instructor> getAllInstructor(){
		return instructorRepository.findAll();
	}
	
	public List<Course> getAllCourses(){
		return courseRepository.findAll();
	}
	public Instructor getInstructorById(Long id) {
		return instructorRepository.findById(id).orElse(null);
	}
	public boolean courseCodeExists(String code) {
		return courseRepository.existsByCourseCodeIgnoreCase(code);
	}
	
	@Transactional
	public Course saveCourse(Course course) {
		return courseRepository.save(course);
	}
}
