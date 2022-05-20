package com.barlo.project_service.model.dto;

import com.barlo.project_service.model.Project;

public class ProjectMapper {
    public static ProjectDTO map(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .tasks(project.getTasks())
                .status(project.getStatus())
                .build();
    }
}
