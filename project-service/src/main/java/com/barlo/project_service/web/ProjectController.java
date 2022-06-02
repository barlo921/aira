package com.barlo.project_service.web;

import com.barlo.project_service.model.dto.ProjectDTO;
import com.barlo.project_service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/projects")
public class ProjectController {

    private ProjectService service;

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.OK, reason = "Project created")
    public void createProject(@Valid @RequestBody ProjectDTO projectDTO) {
        log.info("Creating project {}", projectDTO);
        service.createProject(projectDTO);
    }

    @PostMapping("/delete")
    @ResponseStatus(value = HttpStatus.OK, reason = "Project deleted")
    public void deleteProject(@Valid @RequestBody ProjectDTO projectDTO) {
        log.info("Deleting project {}", projectDTO);
        service.deleteProject(projectDTO);
    }

    @PostMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Project deleted")
    public void deleteProjectById(
            @Valid
            @DecimalMin(value = "1", message = "Id should be greater or equals 1")
            @PathVariable("id") Long id) {
        log.info("Deleting project with id {}", id);
        service.deleteProjectById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(
            @Valid
            @DecimalMin(value = "1", message = "Should be greater or equal 1")
            @PathVariable("id") Long id) {

        log.info("Searching for project with id {}", id);
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAll() {
        log.info("Get all projects");
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping("/tasks/add/{id}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Task is added")
    public void addTaskToProject(
            @Valid
            @DecimalMin(value = "1", message = "Id should be greater or equals 1")
            @PathVariable("id") Long taskId,
            @RequestBody ProjectDTO projectDTO) {
        log.info("Adding task with id {} to project {}", taskId, projectDTO.getName());
        service.addTask(taskId, projectDTO);
    }

    @PostMapping("/done/{id}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Project is Done")
    public void doneProject(
            @Valid
            @DecimalMin(value = "1", message = "Id should be greater or equals 1")
            @PathVariable("id") Long id) {
        log.info("Done project with id {}", id);
        service.doneProject(id);
    }

}
