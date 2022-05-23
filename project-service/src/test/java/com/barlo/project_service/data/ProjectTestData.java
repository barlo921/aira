package com.barlo.project_service.data;

import com.barlo.project_service.model.Status;
import com.barlo.project_service.model.dto.ProjectDTO;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProjectTestData {

    public static final ProjectDTO PROJECT_1 =
            ProjectDTO.builder()
                    .id(1L)
                    .name("AIRA-F")
                    .tasks(Stream.of(1L).collect(Collectors.toSet()))
                    .build();

    public static final ProjectDTO PROJECT_2 =
            ProjectDTO.builder()
                    .id(2L)
                    .name("AIRA-D")
                    .tasks(Stream.of(2L).collect(Collectors.toSet()))
                    .status(Status.ACTIVE)
                    .build();

    public static final List<ProjectDTO> PROJECTS = Arrays.asList(PROJECT_1, PROJECT_2);

    public static ProjectDTO getCreated() {
        return ProjectDTO.builder()
                .name("NewProject")
                .status(Status.ACTIVE)
                .tasks(new HashSet<Long>())
                .build();
    }

    public static  ProjectDTO getDeleted() {
        return ProjectDTO.builder()
                .name("NewProject")
                .status(Status.DELETED)
                .tasks(new HashSet<Long>())
                .build();
    }

    public static ProjectDTO getDone() {
        return ProjectDTO.builder()
                .name("NewProject")
                .status(Status.DONE)
                .tasks(new HashSet<Long>())
                .build();
    }

    public static ProjectDTO getWithTask() {
        return ProjectDTO.builder()
                .name("NewProject")
                .status(Status.ACTIVE)
                .tasks(Stream.of(3L).collect(Collectors.toSet()))
                .build();
    }

}
