package com.barlo.project_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @SequenceGenerator(
            name = "project_id_sequence",
            sequenceName = "project_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "project_id_sequence"
    )
    private Long id;

    @NotBlank(message = "Project name is required")
    @Size(max = 10, message = "Project name should be 10 characters long or less")
    private String name;

    @ElementCollection
    @CollectionTable(
            name = "project_tasks",
            joinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Long> tasks;

    @Enumerated(EnumType.STRING)
    private Status status;

    public void addTask(Long id) {
        tasks.add(id);
    }

}
