package com.barlo.project_service;

import com.barlo.project_service.exception.ProjectNotFoundException;
import com.barlo.project_service.model.Project;
import com.barlo.project_service.model.Status;
import com.barlo.project_service.model.dto.ProjectDTO;
import com.barlo.project_service.model.dto.ProjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {

    private ProjectRepository repository;

    public Project createProject(ProjectDTO projectDTO) {

        Project project = Project.builder()
                .name(projectDTO.getName())
                .status(Status.ACTIVE)
                .tasks(new HashSet<Long>())
                .build();

        return repository.save(project);
    }

    public void deleteProject(ProjectDTO projectDTO) {
        Project project = repository.findByName(projectDTO.getName());

        if (project == null) {
            throw new ProjectNotFoundException("Project not found");
        }
        project.setStatus(Status.DELETED);

        repository.save(project);
    }

    public void deleteProjectById(Long id) {
        Project project = checkProjectNotFound(id);
        project.setStatus(Status.DELETED);
        repository.save(project);
    }

    public ProjectDTO getById(Long id) {
        Project project = checkProjectNotFound(id);
        return ProjectMapper.mapAllFields(project);
    }

    public List<ProjectDTO> getAll() {
        return repository.findAll().stream()
                .map(ProjectMapper::mapAllFields)
                .collect(Collectors.toList());
    }

    public void addTask(Long taskId, ProjectDTO projectDTO) {
        Project project = checkProjectNotFound(projectDTO.getId());
        project.addTask(taskId);
        repository.save(project);

    }

    public void doneProject(Long id) {
        Project project = checkProjectNotFound(id);
        project.setStatus(Status.DONE);
        repository.save(project);
    }

    private Project checkProjectNotFound(Long id) {
        Project project = repository.findById(id).orElse(null);
        if (repository.findById(id).orElse(null) == null) {
            throw new ProjectNotFoundException("Project not found");
        }
        return project;
    }

}
