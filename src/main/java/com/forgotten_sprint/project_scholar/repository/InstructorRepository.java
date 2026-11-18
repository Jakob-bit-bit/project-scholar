package com.forgotten_sprint.project_scholar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forgotten_sprint.project_scholar.model.Instructor;

@Repository
public interface InstructorRepository 
	extends JpaRepository<Instructor, Long>{
}
