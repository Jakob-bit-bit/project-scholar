package com.forgotten_sprint.project_scholar.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.forgotten_sprint.project_scholar.model.Course;
import com.forgotten_sprint.project_scholar.model.CourseStatus;

public interface CourseCatalogRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE " +
           "(:category IS NULL OR c.category = :category) AND " +
           "(:status IS NULL OR c.status = :status)")
    Page<Course> search(@Param("category") String category,
                        @Param("status") CourseStatus status,
                        Pageable pageable);

    @Query("SELECT DISTINCT c.category FROM Course c ORDER BY c.category")
    List<String> findAllCategories();
}
