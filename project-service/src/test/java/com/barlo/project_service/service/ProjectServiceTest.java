package com.barlo.project_service.service;

import com.barlo.project_service.AbstractTest;
import com.barlo.project_service.ProjectService;
import com.barlo.project_service.data.ProjectTestData;
import com.barlo.project_service.model.Project;
import com.barlo.project_service.model.dto.ProjectDTO;
import com.barlo.project_service.model.dto.ProjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static com.barlo.project_service.data.ProjectTestData.*;

@SpringBootTest
@Sql(scripts = "classpath:db/populateH2.sql")
class ProjectServiceTest extends AbstractTest {

    @Autowired
    ProjectService service;

    @Test
    @Transactional
    void createProjectTest() {
        ProjectDTO createdProject = getCreated();
        Project project = service.createProject(createdProject);
        createdProject.setId(project.getId());
        Assertions.assertEquals(createdProject, service.getById(project.getId()));
    }

    @Test
    @Transactional
    void deleteProjectTest() {
        ProjectDTO toDeletionProject = getCreated();

        Project project = service.createProject(toDeletionProject);
        service.deleteProject(ProjectMapper.mapWithoutTasks(project));

        ProjectDTO deletedProject = getDeleted();
        deletedProject.setId(project.getId());

        Assertions.assertEquals(deletedProject, service.getById(project.getId()));
    }

    @Test
    @Transactional
    void deleteProjectByIdTest() {
        ProjectDTO toDeletionProject = getCreated();

        Project project = service.createProject(toDeletionProject);
        service.deleteProjectById(project.getId());

        toDeletionProject = service.getById(project.getId());

        ProjectDTO deletedProject = getDeleted();
        deletedProject.setId(project.getId());

        Assertions.assertEquals(deletedProject, toDeletionProject);
    }

    @Test
    @Transactional
    void getByIdTest() {
        ProjectDTO projectDTO = ProjectTestData.PROJECT_1;
        ProjectDTO getProject = service.getById(1L);

        Assertions.assertEquals(projectDTO, getProject);
    }

    @Test
    @Transactional
    void getAllTest() {
        Assertions.assertEquals(service.getAll(), ProjectTestData.PROJECTS);
    }

    @Test
    @Transactional
    void addTaskTest() {
        ProjectDTO toAddTaskProject = getCreated();
        Project project = service.createProject(toAddTaskProject);
        toAddTaskProject.setId(project.getId());
        service.addTask(3L, toAddTaskProject);

        toAddTaskProject = service.getById(project.getId());

        ProjectDTO withTaskProject = getWithTask();
        withTaskProject.setId(project.getId());

        Assertions.assertEquals(withTaskProject, toAddTaskProject);
    }

    @Test
    @Transactional
    void doneProjectTest() {
        ProjectDTO toDoneProject = getCreated();
        Project project = service.createProject(toDoneProject);
        service.doneProject(project.getId());

        toDoneProject = service.getById(project.getId());

        ProjectDTO doneProject = getDone();
        doneProject.setId(project.getId());

        Assertions.assertEquals(doneProject, toDoneProject);
    }
}