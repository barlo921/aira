package com.barlo.project_service;

import com.barlo.project_service.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByName(String name);
}
