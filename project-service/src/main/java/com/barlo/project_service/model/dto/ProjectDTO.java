package com.barlo.project_service.model.dto;

import com.barlo.project_service.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    @DecimalMin("1")
    private Long id;

    @NotBlank(message = "Project name is required")
    @Size(max = 10, message = "Project name should be 10 characters long or less")
    private String name;

    private Set<Long> tasks;

    @Enumerated(EnumType.STRING)
    private Status status;
}
