package com.barlo.project_service.web;

import com.barlo.project_service.AbstractTest;
import com.barlo.project_service.ProjectService;
import com.barlo.project_service.exception.ProjectNotFoundException;
import com.barlo.project_service.model.dto.ProjectDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static com.barlo.project_service.data.ProjectTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest extends AbstractTest {

    @MockBean
    private ProjectService projectService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createProjectTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getCreated())))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.status().reason("Project created"));

    }

    @Test
    void createProjectNoBodyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/create"))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void createProjectBodyWithoutNameTest() throws Exception {
        ProjectDTO projectDTO = getCreated();
        projectDTO.setName("");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(projectDTO)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void createProjectWithNameLongerThenTenTest() throws Exception {
        ProjectDTO projectDTO = getCreated();
        projectDTO.setName("New Project test");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(projectDTO)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void deleteProjectTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/delete/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getCreated())))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.status().reason("Project deleted"));
    }

    @Test
    void deleteProjectBodyWithoutNameTest() throws Exception {
        ProjectDTO projectDTO = getCreated();
        projectDTO.setName("");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(projectDTO)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void deleteProjectNotFoundTest() throws Exception {
        Mockito.doThrow(new ProjectNotFoundException("Project not found")).when(projectService).deleteProject(getCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getCreated())))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(content().string("Project not found"));
    }

    @Test
    void deleteProjectNoBodyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/delete"))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void deleteProjectByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.status().reason("Project deleted"));

    }

    @Test
    void deleteProjectByIdBadIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/delete/0"))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void getProjectByIdTest() throws Exception {
        Mockito.when(projectService.getById(1L)).thenReturn(PROJECT_1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/1"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(4)))
                .andExpect(jsonPath("id", Matchers.is(1)))
                .andExpect(jsonPath("name", Matchers.is("AIRA-F")))
                .andExpect(jsonPath("status", Matchers.is(Matchers.nullValue())))
                .andExpect(jsonPath("tasks", Matchers.containsInAnyOrder(1)));
    }

    @Test
    void getAllTest() throws Exception {
        Mockito.when(projectService.getAll()).thenReturn(PROJECTS);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("AIRA-F")))
                .andExpect(jsonPath("$[0].status", Matchers.is(Matchers.nullValue())))
                .andExpect(jsonPath("$[0].tasks", Matchers.containsInAnyOrder(1)))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(jsonPath("$[1].name", Matchers.is("AIRA-D")))
                .andExpect(jsonPath("$[1].status", Matchers.is("ACTIVE")))
                .andExpect(jsonPath("$[1].tasks", Matchers.containsInAnyOrder(2)));
    }

    @Test
    void addTaskToProjectTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/tasks/add/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getCreated())))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.status().reason("Task is added"));
    }

    @Test
    void addTaskToProjectNoBodyTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/tasks/add/1"))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void addTaskToProjectWithoutIdTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/tasks/add/"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void addTaskToProjectWrongIdTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/tasks/add/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getCreated())))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void addTaskToProjectBodyNotValidTest() throws Exception{
        ProjectDTO projectDTO = getCreated();
        projectDTO.setName("");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/tasks/add/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(projectDTO)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void addTaskProjectNotFoundTest() throws Exception{
        Mockito.doThrow(new ProjectNotFoundException("Project not found")).when(projectService).addTask(1L, getCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/tasks/add/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getCreated())))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(content().string("Project not found"));

    }

    @Test
    void doneProjectTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/done/1"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.status().reason("Project is Done"));
    }

    @Test
    void doneProjectWithoutIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/done/"))
                .andExpect(MockMvcResultMatchers.status().is(405));
    }

    @Test
    void doneProjectWithWrongIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/done/0"))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void doneProjectWithIdProjectNotFoundTest() throws Exception {
        Mockito.doThrow(new ProjectNotFoundException("Project not found")).when(projectService).doneProject(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/done/1"))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(content().string("Project not found"));
    }
}