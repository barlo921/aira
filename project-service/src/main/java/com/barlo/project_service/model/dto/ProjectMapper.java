package com.barlo.project_service.model.dto;

import com.barlo.project_service.model.Project;
import org.springframework.transaction.annotation.Transactional;

public class ProjectMapper {

    public static ProjectDTO mapWithoutTasks(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .status(project.getStatus())
                .build();
    }

    @Transactional
    public static ProjectDTO mapAllFields(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .tasks(project.getTasks())
                .status(project.getStatus())
                .build();
    }
}
